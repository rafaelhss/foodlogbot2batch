package com.foodlog.sender;

import com.foodlog.sender.sentmessage.SentMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Service
public class Sender {

    @Autowired
    private SentMessageService sentMessageService;

    private String botId;
    protected String UrlTemplate      = "https://api.telegram.org/bot@@BOTID@@/sendmessage?chat_id=@@CHATID@@&text=@@TEXT@@";
    protected String UrlImageTemplate = "https://api.telegram.org/bot@@BOTID@@/sendPhoto?chat_id=@@CHATID@@";

    //para testes
    public Sender(){
        this.botId = "TESTE";
        this.UrlTemplate = UrlTemplate.replace("@@BOTID@@", botId);
    }

    public Sender init(String botId){
        this.botId = botId;
        this.UrlTemplate = UrlTemplate.replace("@@BOTID@@", botId);
        return this;
    }

    private void sendResponse(Integer id, String text_response) {
        try {
            System.out.println("Sending response....");
            text_response = URLEncoder.encode(text_response, "UTF-8");
            URL url = new URL(UrlTemplate.replace("@@CHATID@@", id.toString()).replace("@@TEXT@@", text_response));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            System.out.println(url);
            //BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        } catch (IOException ex) {
            System.out.println("errrxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx: " + ex.getMessage());
            ex.printStackTrace();
        }

    }

    public void sendResponse(Integer id, String text_response, boolean noRepeat){
        if(noRepeat) {
            sentMessageService.clearAllByPastDays(1, this.getClass().toString());
            if (sentMessageService.isSent(text_response + id.toString().hashCode())) {
                System.out.println("Mensagem ja enviada: " + text_response + id.toString().hashCode());
                return;
            }
        }
        sentMessageService.logSentMessage(text_response + id.toString().hashCode(), "NO_REPEAT");
        sendResponse(id, text_response);
    }

    public void sendImage(Integer id, byte[] image){
        URL url = null;
        try {
            url = new URL(UrlTemplate.replace("@@CHATID@@", id.toString()));

            StringBuilder parameters = new StringBuilder();

            parameters.append("file1=");
            parameters.append(URLEncoder.encode(new String(image),"UTF-8"));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("charset","UTF-8");
            connection.setRequestProperty("Content-Length",Integer.toString(parameters.toString().getBytes().length));

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream ());
            wr.writeBytes(parameters.toString());
            wr.flush();
            wr.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
