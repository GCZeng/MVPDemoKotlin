package mvpdemo.zgc.mvpdemokotlin.util.http

import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

import okhttp3.Connection
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.internal.http.HttpHeaders
import okhttp3.internal.platform.Platform
import okio.Buffer
import okio.BufferedSource

import okhttp3.internal.platform.Platform.INFO

/**
 * Created by Nick on 2017/12/10
 */
class HttpLogInterceptor @JvmOverloads constructor(private val logger: HttpLogInterceptor.Logger = HttpLogInterceptor.Logger.DEFAULT) : Interceptor {

    @Volatile
    private var level: HttpLogInterceptor.Level = HttpLogInterceptor.Level.NONE

    enum class Level {
        /** No logs.  */
        NONE,
        /**
         * Logs request and response lines.
         *
         *
         * Example:
         * <pre>`--> POST /greeting http/1.1 (3-byte body)
         *
         * <-- 200 OK (22ms, 6-byte body)
        `</pre> *
         */
        BASIC,
        /**
         * Logs request and response lines and their respective headers.
         *
         *
         * Example:
         * <pre>`--> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
        `</pre> *
         */
        HEADERS,
        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         *
         *
         * Example:
         * <pre>`--> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         *
         * Hi?
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         *
         * Hello!
         * <-- END HTTP
        `</pre> *
         */
        BODY
    }

    interface Logger {
        fun log(message: String)

        companion object {

            /** A [HttpLogInterceptor.Logger] defaults output appropriate for the current platform.  */
            val DEFAULT: HttpLogInterceptor.Logger = object : HttpLogInterceptor.Logger {
                override fun log(message: String) {
                    Platform.get().log(INFO, message, null)
                }
            }
        }
    }

    /** Change the level at which this interceptor logs.  */
    fun setLevel(level: HttpLogInterceptor.Level?): HttpLogInterceptor {
        if (level == null) throw NullPointerException("level == null. Use Level.NONE instead.")
        this.level = level
        return this
    }

    fun getLevel(): HttpLogInterceptor.Level {
        return level
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val level = this.level

        val logStr = StringBuffer()

        val request = chain.request()
        if (level == HttpLogInterceptor.Level.NONE) {
            return chain.proceed(request)
        }

        val logBody = level == HttpLogInterceptor.Level.BODY
        val logHeaders = logBody || level == HttpLogInterceptor.Level.HEADERS

        val requestBody = request.body()
        val hasRequestBody = requestBody != null

        val connection = chain.connection()
        var requestStartMessage = ("--> "
                + request.method()
                + ' '.toString() + request.url()
                + if (connection != null) " " + connection.protocol() else "")
        if (!logHeaders && hasRequestBody) {
            requestStartMessage += " (" + requestBody!!.contentLength() + "-byte body)"
        }
        logStr.append(requestStartMessage)
        logStr.append("\n")


        if (logHeaders) {
            if (hasRequestBody) {
                // Request body headers are only present when installed as a network interceptor. Force
                // them to be included (when available) so there values are known.
                if (requestBody!!.contentType() != null) {
                    logStr.append("Content-Type: " + requestBody.contentType()!!)
                    logStr.append("\n")
                }
                if (requestBody.contentLength() != -1L) {
                    logStr.append("Content-Length: " + requestBody.contentLength())
                    logStr.append("\n")
                }
            }

            val headers = request.headers()
            var i = 0
            val count = headers.size()
            while (i < count) {
                val name = headers.name(i)
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equals(name, ignoreCase = true) && !"Content-Length".equals(name, ignoreCase = true)) {
                    logStr.append(name + ": " + headers.value(i))
                    logStr.append("\n")
                }
                i++
            }

            if (!logBody || !hasRequestBody) {
                logStr.append("--> END " + request.method())
                logStr.append("\n")
            } else if (bodyEncoded(request.headers())) {
                logStr.append("--> END " + request.method() + " (encoded body omitted)")
                logStr.append("\n")
            } else {
                val buffer = Buffer()
                requestBody!!.writeTo(buffer)

                var charset: Charset? = UTF8
                val contentType = requestBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)
                }

                logStr.append("")
                logStr.append("\n")
                if (isPlaintext(buffer)) {
                    logStr.append(buffer.readString(charset!!))
                    logStr.append("\n")
                    logStr.append("--> END " + request.method() + " (" + requestBody.contentLength() + "-byte body)")
                    logStr.append("\n")
                } else {
                    logStr.append("--> END " + request.method() + " (binary " + requestBody.contentLength() + "-byte body omitted)")
                    logStr.append("\n")
                }
            }
        }
        val startNs = System.nanoTime()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            logStr.append("<-- HTTP FAILED: $e")
            logStr.append("\n")
            throw e
        }

        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        val responseBody = response.body()
        val contentLength = responseBody!!.contentLength()
        val bodySize = if (contentLength != -1L) contentLength.toString() + "-byte" else "unknown-length"
        logStr.append("<-- "
                + response.code()
                + (if (response.message().isEmpty()) "" else ' ' + response.message())
                + ' '.toString() + response.request().url()
                + " (" + tookMs + "ms" + (if (!logHeaders) ", $bodySize body" else "") + ')'.toString())
        logStr.append("\n")
        if (logHeaders) {
            val headers = response.headers()
            var i = 0
            val count = headers.size()
            while (i < count) {
                logStr.append(headers.name(i) + ": " + headers.value(i))
                logStr.append("\n")
                i++
            }

            if (!logBody || !HttpHeaders.hasBody(response)) {
                logStr.append("<-- END HTTP")
                logStr.append("\n")
            } else if (bodyEncoded(response.headers())) {
                logStr.append("<-- END HTTP (encoded body omitted)")
                logStr.append("\n")
            } else {
                val source = responseBody.source()
                source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
                val buffer = source.buffer()

                var charset: Charset? = UTF8
                val contentType = responseBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)
                }

                if (!isPlaintext(buffer)) {
                    logStr.append("")
                    logStr.append("\n")
                    logStr.append("<-- END HTTP (binary " + buffer.size() + "-byte body omitted)")
                    logStr.append("\n")
                    return response
                }

                if (contentLength != 0L) {
                    logStr.append("")
                    logStr.append("\n")
                    logStr.append(buffer.clone().readString(charset!!))
                    logStr.append("\n")
                }

                logStr.append("<-- END HTTP (" + buffer.size() + "-byte body)")
                logStr.append("\n")
            }
        }
        logger.log(logStr.toString())
        return response
    }

    private fun bodyEncoded(headers: Headers): Boolean {
        val contentEncoding = headers.get("Content-Encoding")
        return contentEncoding != null && !contentEncoding.equals("identity", ignoreCase = true)
    }

    companion object {
        private val UTF8 = Charset.forName("UTF-8")

        /**
         * Returns true if the body in question probably contains human readable text. Uses a small sample
         * of code points to detect unicode control characters commonly used in binary file signatures.
         */
        internal fun isPlaintext(buffer: Buffer): Boolean {
            try {
                val prefix = Buffer()
                val byteCount = if (buffer.size() < 64) buffer.size() else 64
                buffer.copyTo(prefix, 0, byteCount)
                for (i in 0..15) {
                    if (prefix.exhausted()) {
                        break
                    }
                    val codePoint = prefix.readUtf8CodePoint()
                    if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                        return false
                    }
                }
                return true
            } catch (e: EOFException) {
                return false // Truncated UTF-8 sequence.
            }

        }
    }
}
