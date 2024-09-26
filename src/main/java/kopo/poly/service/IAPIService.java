package kopo.poly.service;

import kopo.poly.dto.LibraryItemDTO;
import kopo.poly.dto.ShoppingDTO;

public interface IAPIService {

    ShoppingDTO getShoppingList(String title) throws Exception;

    LibraryItemDTO getLibraryItem(String title) throws Exception;

    String getDataLibraryList(String title, String manageCd) throws Exception;

    String getNlList(String title)throws Exception;
}
