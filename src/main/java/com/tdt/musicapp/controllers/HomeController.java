package com.tdt.musicapp.controllers;

import com.braintreegateway.BraintreeGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import com.tdt.musicapp.dto.domain.ActiveServiceDTO;
import com.tdt.musicapp.dto.domain.PaymentCheckoutDTO;
import com.tdt.musicapp.dto.domain.transaction.TargetDTO;
import com.tdt.musicapp.dto.domain.transaction.TransactionDTO;
import com.tdt.musicapp.dto.response.LoginResponseDTO;
import com.tdt.musicapp.service.braintree.TdBraintreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.io.ByteArrayInputStream;
import java.util.*;

/**
 */

@Controller
public class HomeController extends BaseController {


    @Autowired
    private TdBraintreeService tdBraintreeService;
    @Autowired
    ViewResolver viewResolver;
//    private Environment env;
//    @Autowired
//    private RestTemplate restTemplate;
//    @Autowired
//    private HttpSession session;


    @RequestMapping(value = {"/", "/home", "/index"})
    String index(){
        if(session.getAttribute("authCode") == null)
            return "login/login";
        else return "home/home";
    }

    @RequestMapping(value = {"/more"})
    String more(){

        return "home/more";
    }

    @RequestMapping(value = {"/profile"}, method = {RequestMethod.GET})
    String profile(){

        return "profile/profile";
    }

    @RequestMapping(value = "/token",
            method = {RequestMethod.POST, RequestMethod.GET},
            produces = "application/json")
    @ResponseBody
    protected LoginResponseDTO getToken() {

        try {
            BraintreeGateway gateway = tdBraintreeService.getBraintreeGateway();
            String s = gateway.clientToken().generate();

            System.out.println(s);
            return LoginResponseDTO.builder()
                    .authToken((String) session.getAttribute("authToken"))
                    .authCode((String) session.getAttribute("authCode"))
                    .build();
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/braintree/token", method = {RequestMethod.POST, RequestMethod.GET},
            produces = "text/plain")
    @ResponseBody
    protected String getBraintreeToken() {

        try {
//            BraintreeGateway gateway = tdBraintreeService.getBraintreeGateway();
//            String s = gateway.clientToken().generate();

            HttpEntity httpEntity = new HttpEntity(getHeaders(null));
            LinkedHashMap<String, String> result = restTemplate.postForObject(
                    env.getProperty("payment_token_url"), httpEntity, LinkedHashMap.class);
            String s = (String)result.get("clientToken");

            return s;
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/braintree/checkout",
            method = {RequestMethod.POST},
            produces = "application/json")
    @ResponseBody
    protected ResponseEntity braintreeCheckout(@RequestBody PaymentCheckoutDTO paymentCheckoutDTO, HttpServletResponse response) throws IOException {

        try {

            MultiValueMap<String, String> params = paymentCheckoutDTO.toMap();

            HttpHeaders headers = getHeaders(null);
            headers.add("Accept", "application/json");
            headers.add("Accept-Encoding", "gzip, deflate, sdch");

            HttpEntity httpEntity = new HttpEntity(params, headers);
            LinkedHashMap maps = restTemplate.postForObject(env.getProperty("payment_checkout_url"),
                    httpEntity, LinkedHashMap.class);
            maps.put("paymentData", paymentCheckoutDTO);
            Integer responseStatus = (Integer)maps.get("status");
            if(responseStatus!=null && responseStatus==0){

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(maps);
            }
            String resultString = (String)maps.get("message");
            ObjectMapper mapper = new ObjectMapper();
            TargetDTO targetDTO = mapper.readValue(resultString, TargetDTO.class);
            TransactionDTO transactionDTO = targetDTO.getTarget();
            Set<String> currentServices = StringUtils.commaDelimitedListToSet(paymentCheckoutDTO.getServices());
            List<LinkedHashMap> services = restTemplate.postForObject(env.getProperty("get_active_service_url"), httpEntity, List.class);

            List<ActiveServiceDTO> newList = new ArrayList<>();
            for(LinkedHashMap service : services){
                if(currentServices.contains(service.get("id").toString())){
                    String s = mapper.writeValueAsString(service);
                    newList.add(mapper.readValue( s, ActiveServiceDTO.class));
                }
            }
            transactionDTO.setActiveServices(newList);
            transactionDTO.setMusicTitle(paymentCheckoutDTO.getMusicName());
            getSession().setAttribute("transaction", transactionDTO);

            Context ctx = new Context(Locale.ENGLISH);
            ctx.setVariable("transaction", transactionDTO);
            String htmlContent = htmlTemplateRenderer("invoice/invoice","templates/", ".html", "XHTML", "UTF-8", ctx);
            maps.put("invoice", htmlContent);

            return ResponseEntity.ok(maps);


        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @RequestMapping(value = "/invoice/download", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    protected void invoiceDownload(HttpServletResponse response) throws IOException {

        try {

            TransactionDTO transactionDTO = (TransactionDTO) getSession().getAttribute("transaction");
            Context ctx = new Context(Locale.ENGLISH);
            ctx.setVariable("transaction", transactionDTO);
            String htmlContent = htmlTemplateRenderer("invoice/invoice","templates/", ".html", "XHTML", "UTF-8", ctx);

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();

            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(os);

            byte[] pdfAsBytes = os.toByteArray();
            os.close();

            response.setContentType("application/pdf");
            response.setContentLengthLong(pdfAsBytes.length);
            String fileName = "Invoice-"+transactionDTO.getId()+"-"+transactionDTO.getCreatedAt().toString();
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
            response.getOutputStream().write(pdfAsBytes);
            response.flushBuffer();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private JavaMailSender javaMailService;

    @RequestMapping(value = "/invoice/email", method = {RequestMethod.POST, RequestMethod.POST})
    @ResponseBody
    protected void invoiceEmail(HttpServletResponse response) throws IOException {

        try {

            TransactionDTO transactionDTO = (TransactionDTO) getSession().getAttribute("transaction");
            Context ctx = new Context(Locale.ENGLISH);
            ctx.setVariable("transaction", transactionDTO);
            String htmlContent = htmlTemplateRenderer("invoice/invoice","templates/", ".html", "XHTML", "UTF-8", ctx);

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();

            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(os);

            byte[] pdfAsBytes = os.toByteArray();
            os.close();

            SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
            simpleMailMessage.setTo("tunedoor.music@gmail.com");
            simpleMailMessage.setFrom("tunedoor.music@gmail.com");
            simpleMailMessage.setSubject("Invoice");
            simpleMailMessage.setText("Hello... invoice");

            MimeMessage message = javaMailService.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(simpleMailMessage.getFrom());
            helper.setTo(simpleMailMessage.getTo());
            helper.setSubject(simpleMailMessage.getSubject());
            helper.setText(simpleMailMessage.getText());
            helper.addAttachment("Invoice", new ByteArrayResource(pdfAsBytes));
            javaMailService.send(message);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String htmlTemplateRenderer(String name, String path, String suffix,
                                        String mode, String encoding, Context context){
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
//        templateResolver.setPrefix("templates/");
        templateResolver.setPrefix(path);
//        templateResolver.setSuffix(".html");
        templateResolver.setSuffix(suffix);
//        templateResolver.setTemplateMode("XHTML");
        templateResolver.setTemplateMode(mode);
//        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCharacterEncoding(encoding);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        String htmlContent = templateEngine.process(name, context);;
        return  htmlContent;
    }


    @RequestMapping(value = {"/user/profile", "/profile"},method = {RequestMethod.POST, RequestMethod.GET},produces = "application/json")
    @ResponseBody
    protected ResponseEntity getProfile() {

        HttpEntity httpEntity = new HttpEntity(getHeaders(null));
        try {
            LinkedHashMap profile = restTemplate.postForObject(env.getProperty("profile_url"), httpEntity, LinkedHashMap.class);
            String responseStatus = (String) profile.get("requestSuccess");
            if(responseStatus != null && !responseStatus.equalsIgnoreCase("true")){
                return ResponseEntity.badRequest().body(profile);
            }
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected HttpSession getSession() {
        return session;
    }
}


/*
  @RequestMapping(
    value = Array("/api/idea/member/display/{fileId}"),
    method = Array(RequestMethod.GET)
  )
  def display( @PathVariable("fileId") fileId : JLong, response : HttpServletResponse) {
    val file = memberIdeaFileService.downloadFile(fileId)
    response.setContentType(file.getObjectMetadata.getContentType)
    response.setContentLengthLong(file.getObjectMetadata.getContentLength)
    val is : InputStream = file.getObjectContent
    IOUtils.copy(is, response.getOutputStream())
    response.flushBuffer()
  }

  @RequestMapping(
    value = Array("/api/idea/member/download/{fileId}"),
    method = Array(RequestMethod.GET)
  )
  def download( @PathVariable("fileId") fileId : JLong, response : HttpServletResponse) {
    val file = memberIdeaFileService.downloadFile(fileId)
    response.setContentType(file.getObjectMetadata.getContentType)
    response.setContentLengthLong(file.getObjectMetadata.getContentLength)
    response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getKey))
    val is : InputStream = file.getObjectContent
    IOUtils.copy(is, response.getOutputStream())
    response.flushBuffer()
  }


* */