package com.foodlog.sender;

import com.foodlog.config.TelegramConfig;
import com.foodlog.sender.sentmessage.SentMessageService;
import com.foodlog.user.User;
import com.foodlog.user.UserTelegram;
import com.foodlog.user.UserTelegramAdapter;
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

    @Autowired
    private UserTelegramAdapter userTelegramAdapter;

    //para testes
    public Sender(){
        this.botId = TelegramConfig.BOT_ID;
        this.UrlTemplate = UrlTemplate.replace("@@BOTID@@", botId);
    }

    private boolean sendResponse(Integer id, String text_response) {
        try {
            System.out.println("Sending response....");
            text_response = URLEncoder.encode(text_response, "UTF-8");
            URL url = new URL(UrlTemplate.replace("@@CHATID@@", id.toString()).replace("@@TEXT@@", text_response));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            System.out.println(url);
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            return true;

        } catch (IOException ex) {
            System.out.println("errrxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean sendResponse(User user, String text_response) {
        UserTelegram userTelegram = userTelegramAdapter.getUserByLogin(user);
        if(userTelegram == null){
            System.out.println("Usuario nao encontrado: " + user);
            return false;
        } else {
            return sendResponse(userTelegram.getTelegramId(), text_response);
        }
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
