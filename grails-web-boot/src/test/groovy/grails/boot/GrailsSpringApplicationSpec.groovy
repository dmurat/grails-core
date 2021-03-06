package grails.boot

import grails.boot.config.GrailsWebConfiguration
import org.springframework.boot.SpringApplication
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import spock.lang.Specification

/**
 * Created by graemerocher on 28/05/14.
 */
class GrailsSpringApplicationSpec extends Specification{

    ConfigurableApplicationContext context

    void cleanup() {
        context.close()
    }

    void "Test run Grails via SpringApplication"() {
        when:"SpringApplication is used to run a Grails app"
            context = SpringApplication.run(Application)

        then:"The application runs"
            context != null
            new URL("http://localhost:${context.embeddedServletContainer.port}/foo/bar").text == 'hello world'
    }


    @Configuration
    static class Application extends GrailsWebConfiguration {
        @Bean
        public EmbeddedServletContainerFactory containerFactory() {
            return new TomcatEmbeddedServletContainerFactory(0);
        }
    }
}
