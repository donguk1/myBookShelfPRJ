package kopo.poly.service;

import kopo.poly.dto.ShoppingDTO;

public interface INaverService {

    ShoppingDTO getShoppingList(String title) throws Exception;
}
