package kopo.poly.service.impl;


import kopo.poly.repository.BoardRepository;
import kopo.poly.service.IBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService implements IBoardService {

    private final BoardRepository boardRepository;
}
