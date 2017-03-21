package com.tdt.musicapp.controllers.music;

import com.tdt.musicapp.controllers.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@Slf4j
public class RecorderController extends BaseController {

    @RequestMapping(value = {"/music/save", "/music/record"}, method = RequestMethod.POST)
    @ResponseBody
    public String saveMusic(MultipartFile file, String title) throws IOException {

        String urlString = env.getProperty("upload_url");
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost postRequest = new HttpPost(urlString);

        Header header1 = new BasicHeader("authCode", (String) getSession().getAttribute("authCode"));
        Header header2 = new BasicHeader("authToken", (String) getSession().getAttribute("authToken"));
        Header header3 = new BasicHeader("userAgent", (String) getSession().getAttribute("userAgent"));

        postRequest.setHeaders(new Header[]{header1, header2, header3});

        // build request parameters
        StringBody authCode = new StringBody((String) getSession().getAttribute("authCode"), ContentType.MULTIPART_FORM_DATA);
        StringBody authToken = new StringBody((String) getSession().getAttribute("authToken"), ContentType.MULTIPART_FORM_DATA);
        StringBody tempo = new StringBody("0", ContentType.MULTIPART_FORM_DATA);
        StringBody signature = new StringBody("3/4", ContentType.MULTIPART_FORM_DATA);
        StringBody titleStr = new StringBody(title, ContentType.MULTIPART_FORM_DATA);
        ByteArrayBody fileBody = new ByteArrayBody(file.getBytes(), "body.wav");

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addPart("authCode", authCode);
        builder.addPart("authToken", authToken);
        builder.addPart("musicData", fileBody);
        builder.addPart("tempo", tempo);
        builder.addPart("signature", signature);
        builder.addPart("title", titleStr);
        postRequest.setEntity(builder.build());
        httpClient.execute(postRequest);
        // TODO return is important?
        return "true";
    }

    @Override
    protected HttpSession getSession() {
        return session;
    }
}
