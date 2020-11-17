package vt.qlkdtt.yte.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.service.LoginService;
import viettel.passport.client.UserToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    @Autowired
    ResourceLoader resourceLoader;
    public UserToken getUserData() {
        UserToken userToken = new UserToken();
        try {
            userToken = UserToken.parseXMLResponse(getDataTest());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userToken;
    }

    public String getDataTest() throws Exception {
        String response;
        Resource resource = resourceLoader.getResource("classpath:data.xml");
        InputStream inputStream = resource.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            response = sb.toString();
        } finally {
            br.close();
        }
        return response;
    }

}
