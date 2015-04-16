package com.google.devrel.training.conference.form;

/**
 * Pojo representing a profile form on the client side.
 */
public class ProfileForm {

	private String name;
	private String surname;
	private String city;
	private String country;

	private String day;
	//private Day day;
	private Month month;
	private String year;
	//private Year year;

	private ProfileForm() {
	}

	public ProfileForm(String name, String surname, String city,
			String country, String day, Month month, String year) {
		this.name = name;
		this.surname = surname;
		this.city = city;
		this.country = country;
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	

	/*public static enum Day {
		o(1), t(2), th(3), f(4), fi(5), s(6), se(7), e(8), n(9), ten(10), oo(11), ot(
				12), oth(13), of(14), ofi(15), os(16), ose(17), oe(18), on(19), tw(
				20), to(21), tt(22), tth(23), tf(24), tfi(25), ts(26), tse(27), te(
				28), tn(29), thir(30), tho(31), NOT_SPECIFIED(0);
		private int value;

		private Day(int value) {
			this.value = value;
		}

	};*/

	public static enum Month {
		Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec, NOT_SPECIFIED

	}
/*
	public static enum Year {
		a(1975), b(1976), c(1977), d(1978), e(1979), f(1980), g(1981), h(1982), i(
				1983), j(1984), k(1985), l(1986), m(1987), n(1988), o(1989), p(
				1990), q(1991), r(1992), s(1993), t(1994), v(1995), w(1996), x(
				1997), u(1998), z(1999), aa(2000), ab(2001), ac(2002), ad(2003), ae(
				2004), af(2005), ag(2006), ah(2007), ai(2008), aj(2009), ak(
				2010), al(2011), am(2012), an(2013), ap(2014), aq(2015), NOT_SPECIFIED(
				0);
		private int value;

		private Year(int value) {
			this.value = value;
		}
	};
*/

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getDay() {
		return day;
	}

	public Month getMonth() {
		return month;
	}

	public String getYear() {
		return year;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

}
