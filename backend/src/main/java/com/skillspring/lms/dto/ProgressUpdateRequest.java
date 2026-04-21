package com.skillspring.lms.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class ProgressUpdateRequest {
  @Min(0)
  @Max(100)
  private Integer progress;

  public Integer getProgress() {
    return progress;
  }

  public void setProgress(Integer progress) {
    this.progress = progress;
  }
}
