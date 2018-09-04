package com.example.medicine.expands;

public enum ResultStates
{
  SUCCEEDED, FAILED;

  private int index;
  private String message;

  public int getIndex()
  {
    return this.index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}