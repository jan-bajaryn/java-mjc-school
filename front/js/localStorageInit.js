class Coupon {
    constructor(name, description, price, creation_date, tags, img) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.creation_date = creation_date;
        this.tags = tags;
        this.img = img;
    }
}

tag_list = ['mountains', 'travel', 'money', 'food', 'nature']
coupon_list = [
    new Coupon("HellCopter ride",
        "Lorem ipsum dolor sit amet, consectetur adipisicing elit.",
        20, new Date(2020, 1, 1, 1, 1, 1, 1),
        [tag_list[0], tag_list[1]]
        , "https://media.istockphoto.com/photos/helicopter-rides-in-illinois-picture-id1092430262?k=6&m=1092430262&s=612x612&w=0&h=9var3MuVhw1RMStXKn5TyVfClQD_vATZQZVeIfAJQpo="
    ),
    new Coupon("Make up",
        "Accusamus accusantium aspernatur culpa" +
        "cumque cupiditate distinctio dolor",
        10, new Date(2020, 2, 1, 1, 1, 1, 1),
        [tag_list[2], tag_list[3]],
        "https://d10qoa1dy3vloz.cloudfront.net/slots-img/blo/blog_nars-hggu7.jpg"
    ),
    new Coupon("Photo session",
        "Ad atque delectus, ea fuga inventore laborum provident.",
        10, new Date(2020, 2, 1, 1, 1, 1, 1),
        [tag_list[4], tag_list[0]],
        "https://image.shutterstock.com/image-photo/soft-focus-women-photographer-hold-260nw-1043596813.jpg"
    ),
    new Coupon("Gym discount",
        "Lorem ipsum dolor sit amet, consectetur.",
        10, new Date(2020, 2, 1, 1, 1, 1, 1),
        [tag_list[1], tag_list[2]],
        "https://i.pinimg.com/originals/7f/50/f0/7f50f05c0c4116b15f7cd3d30dd8c27f.png"
    ),
    new Coupon("Yoga session",
        "Accusamus accusantium aspernatur culpa" +
        "cumque cupiditate distinctio dolor",
        15, new Date(2020, 2, 1, 1, 1, 1, 1),
        [tag_list[3], tag_list[4]],
        "https://cdn.doyou.com/wp/2015/07/8-Ways-Your-Surroundings-Can-Make-or-Break-Your-Yoga-Session.jpg=w768"
    ),
    new Coupon("Driven licence",
        "Lorem ipsum dolor sit amet, consectetur.",
        14, new Date(2020, 2, 1, 1, 1, 1, 1),
        [tag_list[0], tag_list[1]],
        "https://image.shutterstock.com/image-vector/car-driver-license-identification-photo-260nw-610906649.jpg"
    ),
    new Coupon("Planetarium",
        "Lorem ipsum dolor sit amet, consectetur.",
        13, new Date(2020, 2, 1, 1, 1, 1, 1),
        [tag_list[0], tag_list[2]],
        "https://d302e0npexowb4.cloudfront.net/wp-content/uploads/2019/08/14162118/planetarium-Endurescreens.jpg"
    ),
    new Coupon("Cook court",
        "Lorem ipsum dolor sit amet, consectetur.",
        12, new Date(2020, 2, 1, 1, 1, 1, 1),
        [tag_list[0], tag_list[4]],
        "https://images.unsplash.com/photo-1556911220-e15b29be8c8f?ixid=MXwxMjA3fDB8MHxzZWFyY2h8NXx8Y29va3xlbnwwfHwwfA%3D%3D&ixlib=rb-1.2.1&w=1000&q=80"
    )
]


window.localStorage.setItem('tags', JSON.stringify(tag_list));
window.localStorage.setItem('coupons', JSON.stringify(coupon_list));