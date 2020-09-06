package jpabook.springboot_jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable @Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    //실무에서 Setter를 남발하게되면, 유지보수가 3~4배 힘들어지게된다.
    //그래서 웬만하면 Getter만 열어두고, 나머지는 생성자를통해 새로 생성하는 방식으로 해야한다.

    //jpa특성상 기본생성자가없으면 동작하지않으므로 만들어주고, protected를 걸어두면 외부에서 사용이 불가능하다
    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
