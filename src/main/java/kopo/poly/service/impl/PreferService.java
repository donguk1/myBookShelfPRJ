package kopo.poly.service.impl;

import kopo.poly.dto.PreferDTO;
import kopo.poly.repository.PreferRepository;
import kopo.poly.repository.entity.PreferEntity;
import kopo.poly.service.IPreferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PreferService implements IPreferService {

    private final PreferRepository preferRepository;


    @Override
    @Transactional
    public void updatePrefer(String regId, List<String> categories) throws Exception {

        log.info("service updatePrefer");

        this.deleteByRegId(regId);

        categories.forEach(category -> {

            log.info(category);

            preferRepository.save(PreferEntity.builder()
                            .regId(regId)
                            .category(category)
                            .build());
        });

    }

    @Override
    public List<PreferDTO> getPreferList(String regId) throws Exception {

        log.info("service getPreferList");

        return preferRepository.findByRegId(regId)
                .stream()
                .map(PreferDTO::from)
                .collect(Collectors.toList());
    }

    protected void deleteByRegId(String regId) throws Exception {
        preferRepository.deleteAll(preferRepository.findByRegId(regId));
    }

}
