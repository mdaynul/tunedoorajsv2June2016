package com.tdt.musicapp.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Properties;

@Configuration
public class SpringBeansConfig {

    @Bean
    public RestTemplate restTemplate() {

        RestTemplate restTemplate = new RestTemplate();
        HttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        HttpMessageConverter stringHttpMessageConverternew = new StringHttpMessageConverter();
        MappingJackson2XmlHttpMessageConverter jackson2XmlHttpMessageConverter = new MappingJackson2XmlHttpMessageConverter();
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ByteArrayHttpMessageConverter converter = new ByteArrayHttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.ALL));
        restTemplate.setMessageConverters(Arrays.<HttpMessageConverter<?>>asList(
                formHttpMessageConverter, stringHttpMessageConverternew,
                jackson2XmlHttpMessageConverter, jackson2HttpMessageConverter, converter));
        return restTemplate;
    }

    @Bean
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {

        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
        configurer.setLocation(new ClassPathResource("application.properties"));
        return configurer;
    }

    // Set maxPostSize of embedded tomcat server to 10 megabytes (default is 2 MB, not large enough to support file uploads > 1.5 MB)
//    @Bean
//    EmbeddedServletContainerCustomizer containerCustomizer() throws Exception {
//
//        return (ConfigurableEmbeddedServletContainer container) -> {
//            if (container instanceof TomcatEmbeddedServletContainerFactory) {
//                TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
//                tomcat.addConnectorCustomizers(
//                        (connector) -> {
//                            connector.setMaxPostSize(10000000); // 10 MB
//                        });
//            }
//        };
//    }

//    @Bean
//    public JavaMailSenderImpl javaMailSenderImpl(){
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);
//        //Set gmail email id
//        mailSender.setUsername("arvindraivns06@gmail.com");
//        //Set gmail email password
//        mailSender.setPassword("password");
//        Properties prop = mailSender.getJavaMailProperties();
//        prop.put("mail.transport.protocol", "smtp");
//        prop.put("mail.smtp.auth", "true");
//        prop.put("mail.smtp.starttls.enable", "true");
//        prop.put("mail.debug", "true");
//        return mailSender;
//    }
}
