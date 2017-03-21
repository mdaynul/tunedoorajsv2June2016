package com.tdt.musicapp.controllers.music;

import com.tdt.musicapp.controllers.BaseController;
import com.tdt.musicapp.dto.domain.ActiveServiceDTO;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 */

@Controller
public class SongController extends BaseController {

    @RequestMapping(value = {"/song/{id}", "/song/get/{id}"}, produces = "application/octet-stream")
    @ResponseBody
    public byte[] getSong(@PathVariable String id, @RequestParam String userAgent) {
        try {

            HttpHeaders headers = getHeaders(userAgent);
            headers.add("Accept", "*/*");
            headers.add("Accept-Encoding", "gzip, deflate, sdch");
            byte[] result = restTemplate.
                    exchange(env.getProperty("get_song") + "/" + id, HttpMethod.GET, new HttpEntity<>(headers), byte[].class).getBody();
            return result;
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = {"/songs", "/song/list"}, produces = "application/json")
    @ResponseBody
    public List<LinkedHashMap> getSongs(@RequestHeader String userAgent, HttpServletResponse response) {

        HttpEntity httpEntity = new HttpEntity(getHeaders(userAgent));
        try {
            List<LinkedHashMap> songMaps = restTemplate.postForObject(env.getProperty("get_song_url"), httpEntity, List.class);
//            List<SongDTO> songDTOs = restTemplate.postForObject(env.getProperty("get_song_url"), httpEntity, List.class);
//            String userAgent = headers.get("userAgent");
            if(songMaps!=null && songMaps.size() > 0){
                for( LinkedHashMap aDto : songMaps ){
                    aDto.put("url", "/song/"+aDto.get("id")+"?userAgent="+ userAgent);
                }
            }
            return songMaps;
        } catch (Exception e) {

            e.printStackTrace();
            response.setStatus(500);
            return null;
        }
    }

    @RequestMapping(value = {"/gallery", "/song/gallery"})
    @ResponseBody
    public void getGallery(@RequestParam Long id, HttpServletResponse response) {


        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("musicId", Arrays.asList(new String[]{""+id}));

        HttpHeaders headers = getHeaders(null);
        headers.add("Accept", "*/*");
        headers.add("Accept-Encoding", "gzip, deflate, sdch");
        HttpEntity httpEntity = new HttpEntity(params, headers);

        try {
            ResponseEntity entity =  restTemplate.exchange( env.getProperty("gallery_url"),
                    HttpMethod.POST, httpEntity, byte[].class);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(out);
            os.writeObject(entity.getBody());
            response.getOutputStream().write(out.toByteArray());
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
        }
    }

    @RequestMapping(value = {"/song/services", "/song/service/list"}, produces = "application/json")
    @ResponseBody
    public ResponseEntity getActiveService(HttpServletResponse response) {
        HttpEntity httpEntity = new HttpEntity(getHeaders(null));
        try {
            List<ActiveServiceDTO> services =
                    restTemplate.postForObject(env.getProperty("get_active_service_url"), httpEntity, List.class);
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            return null;
        }
    }

    @Override
    protected HttpSession getSession() {
        return session;
    }
}

/*


try {
//            FileOutputStream fos = new FileOutputStream(new File("/tmp/message.pdf"));
//            fos.write(pdfAsBytes);
//            fos.close();

ResponseEntity entity =  restTemplate.exchange(
env.getProperty("gallery_url"),
HttpMethod.POST, httpEntity,
byte[].class);

String fileName = "/tmp/zip" + System.currentTimeMillis() + ".zip";
FileOutputStream fileOutputStream = new FileOutputStream(fileName);
try {
//                fileOuputStream = new FileOutputStream("filename");

ByteArrayOutputStream out = new ByteArrayOutputStream();
ObjectOutputStream os = new ObjectOutputStream(out);
os.writeObject(entity.getBody());
out.toByteArray();

fileOutputStream.write(out.toByteArray());
} finally {
fileOutputStream.close();
}
//            Byte bytes = (Byte) entity.getBody();
InputStream inputStream = new FileInputStream(fileName);
IOUtils.copy(inputStream, response.getOutputStream());
response.flushBuffer();



* */