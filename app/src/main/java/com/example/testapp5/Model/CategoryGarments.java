package com.example.testapp5.Model;

import java.util.ArrayList;
import java.util.List;

public class CategoryGarments
{
    public String categoryGarmentsName;
    public List<ChildGarments> childGarmentsList = new ArrayList<ChildGarments>();

    public CategoryGarments(String categoryGarmentsName, List<ChildGarments> childGarmentsList) {
        this.categoryGarmentsName = categoryGarmentsName;
        this.childGarmentsList = childGarmentsList;
    }
}
