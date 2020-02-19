package com.alsace.example.service.user.domain;

import com.alsace.framework.common.basic.BasePageParam;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = false)
@Table(name = "sys_user")
public class User extends BasePageParam {

  private static final long serialVersionUID = 2752628782288751122L;

  @NotNull
  private String userName;

  @NotNull
  private String password;

}
