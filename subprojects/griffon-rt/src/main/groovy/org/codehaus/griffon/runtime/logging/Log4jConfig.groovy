/*
 * Copyright 2004-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codehaus.griffon.runtime.logging

import griffon.util.Environment
import griffon.util.Metadata
import org.apache.log4j.helpers.LogLog
import org.apache.log4j.varia.NullAppender
import org.apache.log4j.xml.XMLLayout
import org.codehaus.groovy.runtime.InvokerHelper
import org.apache.log4j.*

/**
 * Encapsulates the configuration of Log4j.
 *
 * @author Graeme Rocher (Grails 1.1)
 * @since 0.9.2
 */
class Log4jConfig {
    static final DEFAULT_PATTERN_LAYOUT = new PatternLayout(
            conversionPattern: '%d [%t] %-5p %c{2} %x - %m%n')

    static final LAYOUTS = [xml: XMLLayout, html: HTMLLayout, simple: SimpleLayout, pattern: PatternLayout]
    static final APPENDERS = ["null": NullAppender, console: ConsoleAppender,
            file: FileAppender, rollingFile: RollingFileAppender,
            event: GriffonApplicationEventAppender]

    private appenders = [:]

    def methodMissing(String name, args) {
        if (APPENDERS.containsKey(name) && args) {
            def constructorArgs = args[0] instanceof Map ? args[0] : [:]
            if (!constructorArgs.layout) {
                constructorArgs.layout = DEFAULT_PATTERN_LAYOUT
            }
            def appender = APPENDERS[name].newInstance()
            InvokerHelper.setProperties appender, constructorArgs
            if (appender.name) {
                appenders[appender.name] = appender
            } else {
                LogLog.error "Appender of type $name doesn't define a name attribute, and hence is ignored."
            }
            appender.activateOptions()
            return appenders[name]
        }

        if (LAYOUTS.containsKey(name) && args) {
            return LAYOUTS[name].newInstance(args[0])
        }

        if (isCustomEnvironmentMethod(name, args)) {
            return invokeCallable(args[0])
        }

        LogLog.error "Method missing when configuring log4j: $name"
    }

    private boolean isCustomEnvironmentMethod(String name, args) {
        Environment.current == Environment.CUSTOM &&
                Environment.current.name == name &&
                args && (args[0] instanceof Closure)
    }

    def environments(Closure callable) {
        invokeCallable(callable)
    }

    private invokeCallable(Closure callable) {
        callable.delegate = this
        callable.resolveStrategy = Closure.DELEGATE_FIRST
        callable.call()
    }

    def development(Closure callable) {
        if (Environment.current == Environment.DEVELOPMENT) {
            invokeCallable(callable)
        }
    }

    def production(Closure callable) {
        if (Environment.current == Environment.PRODUCTION) {
            invokeCallable(callable)
        }
    }

    def test(Closure callable) {
        if (Environment.current == Environment.TEST) {
            invokeCallable(callable)
        }
    }

    def configure(Closure callable = {}) {

        Logger root = Logger.getRootLogger()

        callable.delegate = this
        callable.resolveStrategy = Closure.DELEGATE_FIRST

        try {
            callable.call(root)
            for (appender in appenders.values()) root.addAppender appender

            if (!root.allAppenders.hasMoreElements()) {
                def consoleAppender = createConsoleAppender()
                root.setLevel Level.ERROR
                appenders['stdout'] = consoleAppender

                error 'org.codehaus.griffon'

                root.addAppender appenders['stdout']
            }
            /*
            Logger logger = Logger.getLogger("StackTrace")
            logger.additivity = false
            def fileAppender = createFullstackTraceAppender()
            if (!logger.allAppenders.hasMoreElements()) {
                logger.addAppender fileAppender
            }
            */
        }
        catch (Exception e) {
            LogLog.error "WARNING: Exception occured configuring log4j logging: $e.message"
        }
    }

    private createConsoleAppender() {
        def consoleAppender = new ConsoleAppender(layout: DEFAULT_PATTERN_LAYOUT, name: "stdout")
        consoleAppender.activateOptions()
        appenders.console = consoleAppender
        return consoleAppender
    }

    private createFullstackTraceAppender() {
        if (appenders.stacktrace) {
            return appenders.stacktrace
        }

        def fileAppender = new FileAppender(layout: DEFAULT_PATTERN_LAYOUT, name: "stacktraceLog")
        if (Environment.current == Environment.DEVELOPMENT) {
            def targetDir = Metadata.current.getGriffonWorkingDir()
            if (targetDir) targetDir.mkdirs()
            fileAppender.file = targetDir ? "${targetDir.absolutePath}/stacktrace.log" : "stacktrace.log"
        }
        else {
            fileAppender.file = "stacktrace.log"
        }
        fileAppender.activateOptions()
        appenders.stacktrace = fileAppender
        return fileAppender
    }

    Logger root(Closure c) {
        def root = Logger.getRootLogger()

        if (c) {
            c.delegate = new RootLog4jConfig(root, this)
            c.resolveStrategy = Closure.DELEGATE_FIRST
            c.call()
        }

        return root
    }

    def appenders(Closure callable) {
        callable.delegate = this
        callable.resolveStrategy = Closure.DELEGATE_FIRST
        callable.call()
    }

    def appender(Map name, Appender instance) {
        if (name && instance) {
            String appenderName = name.values().iterator().next()
            instance.name = appenderName
            appenders[appenderName] = instance
            instance.activateOptions()
        }
    }

    def appender(Appender instance) {
        if (instance && instance.name) {
            appenders[instance.name] = instance
            instance.activateOptions()
        }
        else {
            LogLog.error "Appender [$instance] is null or does not define a name."
        }
    }

    def off(Map appenderAndPackages) {
        setLogLevelForAppenderToPackageMap(appenderAndPackages, Level.OFF)
    }

    def fatal(Map appenderAndPackages) {
        setLogLevelForAppenderToPackageMap(appenderAndPackages, Level.FATAL)
    }

    def error(Map appenderAndPackages) {
        setLogLevelForAppenderToPackageMap(appenderAndPackages, Level.ERROR)
    }

    def warn(Map appenderAndPackages) {
        setLogLevelForAppenderToPackageMap(appenderAndPackages, Level.WARN)
    }

    def info(Map appenderAndPackages) {
        setLogLevelForAppenderToPackageMap(appenderAndPackages, Level.INFO)
    }

    def debug(Map appenderAndPackages) {
        setLogLevelForAppenderToPackageMap(appenderAndPackages, Level.DEBUG)
    }

    def trace(Map appenderAndPackages) {
        setLogLevelForAppenderToPackageMap(appenderAndPackages, Level.TRACE)
    }

    def all(Map appenderAndPackages) {
        setLogLevelForAppenderToPackageMap(appenderAndPackages, Level.ALL)
    }

    private setLogLevelForAppenderToPackageMap(appenderAndPackages, Level level) {

        def additivity = appenderAndPackages.additivity != null ? appenderAndPackages.remove('additivity') : true

        appenderAndPackages?.each { appender, packages ->
            eachLogger(packages) { Logger logger ->
                logger.level = level
                if (appenders[appender]) {
                    logger.addAppender appenders[appender]
                    logger.additivity = additivity
                }
                else {
                    LogLog.error "Appender $appender not found configuring logger ${logger.getName()}"
                }
            }
        }
    }

    def eachLogger(packages, Closure callable) {
        if (packages instanceof String || packages instanceof GString) {
            Logger logger = Logger.getLogger(packages)
            callable(logger)
        }
        else {
            for (p in packages) {
                p = p?.toString()
                if (p) {
                    Logger logger = Logger.getLogger(p)
                    callable(logger)
                }
            }
        }
    }

    def off(Object[] packages) {
        eachLogger(packages) { logger -> logger.level = Level.OFF }
    }

    def fatal(Object[] packages) {
        eachLogger(packages) { logger -> logger.level = Level.FATAL }
    }

    def error(Object[] packages) {
        eachLogger(packages) { logger -> logger.level = Level.ERROR }
    }

    def warn(Object[] packages) {
        eachLogger(packages) { logger -> logger.level = Level.WARN }
    }

    def info(Object[] packages) {
        eachLogger(packages) { logger -> logger.level = Level.INFO }
    }

    def debug(Object[] packages) {
        eachLogger(packages) { Logger logger -> logger.level = Level.DEBUG }
    }

    def trace(Object[] packages) {
        eachLogger(packages) { logger -> logger.level = Level.TRACE }
    }

    def all(Object[] packages) {
        eachLogger(packages) { logger -> logger.level = Level.ALL }
    }

    def removeAppender(String name) {
        Logger.getRootLogger().removeAppender name
    }
}

class RootLog4jConfig {
    Logger root
    Log4jConfig config

    def RootLog4jConfig(root, config) {
        this.root = root
        this.config = config
    }

    def debug(Object[] appenders = null) {
        setLevelAndAppender(Level.DEBUG, appenders)
    }

    private setLevelAndAppender(Level level, Object[] appenders) {
        root.level = level
        for (appName in appenders) {
            Appender app
            if (appName instanceof Appender) {
                app = appName
            } else {
                app = config.appenders[appName?.toString()]
            }
            if (app) {
                root.addAppender app
            }
        }
    }

    def info(Object[] appenders = null) {
        setLevelAndAppender(Level.INFO, appenders)
    }

    def warn(Object[] appenders = null) {
        setLevelAndAppender(Level.WARN, appenders)
    }

    def trace(Object[] appenders = null) {
        setLevelAndAppender(Level.TRACE, appenders)
    }

    def all(Object[] appenders = null) {
        setLevelAndAppender(Level.ALL, appenders)
    }

    def error(Object[] appenders = null) {
        setLevelAndAppender(Level.ERROR, appenders)
    }

    def fatal(Object[] appenders = null) {
        setLevelAndAppender(Level.FATAL, appenders)
    }

    def off(Object[] appenders = null) {
        setLevelAndAppender(Level.OFF, appenders)
    }

    void setProperty(String s, Object o) {
        root."$s" = o
    }
}
