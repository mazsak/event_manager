package com.example.event_manager.mapper;

import java.util.List;

public interface BasicMapper<CLASS, CLASS_FORM> {

  CLASS_FORM mapToDTO(CLASS object);

  CLASS mapToEntity(CLASS_FORM object);

  List<CLASS_FORM> mapToDTOList(List<CLASS> object);

  List<CLASS> mapToEntityList(List<CLASS_FORM> object);
}
