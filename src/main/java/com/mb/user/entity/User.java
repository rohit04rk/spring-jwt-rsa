package com.mb.user.entity;

import java.util.List;

import org.hibernate.annotations.DynamicUpdate;

import com.mb.common.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private String email;

	@Column(columnDefinition = "boolean default false")
	private Boolean isEmailVerified;

	@Column
	private String profileImg;

	@Column
	private String password;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private List<UserRole> userRoles;

}
