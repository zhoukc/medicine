package com.example.medicine.model.expands;

public  interface IContent<TContent>
{
   void setContent(TContent paramTContent);

   TContent getContent();
}