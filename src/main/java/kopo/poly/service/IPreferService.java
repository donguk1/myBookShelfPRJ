package kopo.poly.service;

import kopo.poly.dto.PreferDTO;

import java.util.List;

public interface IPreferService {

    void updatePrefer(String regId, List<String> categories) throws Exception;

    List<PreferDTO> getPreferList(String regId) throws Exception;
}
