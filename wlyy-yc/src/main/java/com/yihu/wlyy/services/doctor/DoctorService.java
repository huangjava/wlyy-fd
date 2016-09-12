package com.yihu.wlyy.services.doctor;

import com.yihu.wlyy.services.neusoft.NeuSoftWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Airhead on 2016/9/11.
 */
@Service
public class DoctorService {
    @Autowired
    private NeuSoftWebService neuSoftWebService;

    public String getGPTeamInfo(String teamId, String page, String pageSize){
        String gpTeamInfo = neuSoftWebService.getGPTeamInfo(teamId, page, pageSize);

        return gpTeamInfo;
    }

}
