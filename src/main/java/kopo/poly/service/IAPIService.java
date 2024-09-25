package kopo.poly.service;

import kopo.poly.dto.DataLibraryDTO;
import kopo.poly.dto.LibraryItemDTO;
import kopo.poly.dto.ShoppingDTO;

import java.util.List;

public interface IAPIService {

    ShoppingDTO getShoppingList(String title) throws Exception;

    LibraryItemDTO getLibraryItem(String title) throws Exception;

    List<DataLibraryDTO> getDataLibraryList(String title, String manageCd) throws Exception;
}
