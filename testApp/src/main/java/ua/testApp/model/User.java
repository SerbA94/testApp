package ua.testApp.model;

import java.util.Collection;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "usr")
public class User implements UserDetails {

	private static final long serialVersionUID = 1598899194457135919L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String username;
	private String password;
	private String firstName;
	private String lastName;

	private String province;
	private String nativeCity;
	
	@ElementCollection(targetClass = State.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "user_state", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	private Set<State> state;
	
	@ElementCollection(targetClass = Country.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "user_country", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	private Set<Country> country;
	
	
	public String getProvince() {
		return province;
	}
	
	public void setProvince(String province) {
		if(this.country.contains(Country.CANADA)) {
			this.province = province;
		}
	}
	
	public String getNativeCity() {
		return nativeCity;
	}
	
	public void setNativeCity(String nativeCity) {
		if(this.country.contains(Country.CANADA)) {
		this.nativeCity = nativeCity;
		}
	}

	public Set<State> getState() {
		return state;
	}
	public void setState(Set<State> state) {
		if(country.contains(Country.USA)) {
			this.state = state;
		}
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Set<Country> getCountry() {
		return country;
	}
	public void setCountry(Set<Country> country) {
		this.country = country;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isCountryUSA(String inCountry) {
		return Country.USA.name().equals(inCountry);
	}
	public boolean isCountryCanada(String inCountry) {
		return Country.CANADA.name().equals(inCountry);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
	
}
