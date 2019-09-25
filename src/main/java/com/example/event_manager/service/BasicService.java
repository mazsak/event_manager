package com.example.event_manager.service;

import java.util.List;

public interface BasicService<CLASS_FORM, ID> {

  boolean save(final CLASS_FORM object);

  CLASS_FORM saveAndReturn(CLASS_FORM object);

  List<CLASS_FORM> saveAndReturnList(List<CLASS_FORM> objects);

  CLASS_FORM findById(ID id);

  void delete(ID id);

  List<CLASS_FORM> findAll();
}
