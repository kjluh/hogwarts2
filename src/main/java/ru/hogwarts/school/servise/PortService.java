package ru.hogwarts.school.servise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
//import ru.hogwarts.school.model.Port;

@Service
public class PortService {
    @Value("${server.port}")
    private String port;

    public String getPort(){
        return port;
    }
}
