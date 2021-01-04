class Coupon {
    constructor(name, description, price, creation_date, tags, img) {
        this._name = name;
        this._description = description;
        this._price = price;
        this._creation_date = creation_date;
        this._tags = tags;
        this._img = img;
    }

    get name() {
        return this._name;
    }

    get description() {
        return this._description;
    }

    get price() {
        return this._price;
    }

    get creation_date() {
        return this._creation_date;
    }

    get tags() {
        return this._tags;
    }

    get img() {
        return this._img;
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
        "https://d10qoa1dy3vloz.cloudfront.net/slots-img/blo/blog_nars-hggu7.jpg"
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
        "https://d302e0npexowb4.cloudfront.net/wp-content/uploads/2019/08/14162118/planetarium-Endurescreens.jpg"
    )
]

window.localStorage.setItem('tags', tag_list);
window.localStorage.setItem('coupons', coupon_list);

document.addEventListener("DOMContentLoaded", function (event) {
    displayTags();
    displayCoupons(coupon_list);
});

function addTag(i, element) {
    let newChild = document.createElement("div");
    newChild.className = "category_item";
    newChild.style.visibility = 'hidden';
    newChild.innerHTML =
        '        <img src="https://www.beautycolorcode.com/ffffff-1280x1024.png" alt="Category">\n' +
        '        <span>' + tag_list[i] + '</span>\n';
    element.appendChild(newChild);
    return newChild;
}

function displayTags() {
    let element = document.getElementsByClassName('category_list')[0];
    element.innerHTML = '';
    let categoriesRoot = document.getElementById('category');
    let insertion = '';
    let insertionInput = '<option value="" disabled selected>All categories</option>'
    for (let i = 0; i < tag_list.length; i++) {
        insertionInput +=
            '<option value="' + tag_list[i] + '">' + tag_list[i] + '</option>';
    }
    categoriesRoot.innerHTML = insertionInput;
    let time = 1000;
    for (let i = 0; i < tag_list.length; i++) {
        let appended = addTag(i, element);
        setTimeout(() => appended.style.visibility = 'visible', time);
        time = time + 1000;
    }
}

function displayCoupons(coupons_to_display) {
    let element = document.getElementsByClassName('coupon_list')[0];
    let insertion = '';
    for (let i = 0; i < coupons_to_display.length; i++) {
        insertion +=
            '<div class="coupon_card">\n' +
            '            <div class="image_part">\n' +
            '                <img src="' + coupons_to_display[i].img + '" alt="' + coupons_to_display[i].name + '">\n' +
            '            </div>\n' +
            '            <div class="description_part">\n' +
            '                <div class="coupon_name">\n' +
            '                    ' + coupons_to_display[i].name + '\n' +
            '                </div>\n' +
            '                <div class="icon">\n' +
            '                    <i class="material-icons">\n' +
            '                        favorite\n' +
            '                    </i>\n' +
            '                </div>\n' +
            '                <div class="brief_description">\n' +
            '                    ' + coupons_to_display[i].description + '\n' +
            '                </div>\n' +
            '                <div class="expires_in">\n' +
            '                    Expires in 3 days\n' +
            '                </div>\n' +
            '            </div>\n' +
            '            <div class="line"></div>\n' +
            '            <div class="price_part">\n' +
            '                <div class="price">\n' +
            '                    $' + coupons_to_display[i].price + '\n' +
            '                </div>\n' +
            '                <div class="add_to_card">\n' +
            '                    <button>Add to Cart</button>\n' +
            '                </div>\n' +
            '            </div>\n' +
            '        </div>';
    }
    element.innerHTML = insertion;
}

function filter() {
    let inputName = document.getElementById('search');
    let inputTag = document.getElementById('category');

    let value = inputName.value;
    let filtered = coupon_list
        .filter(c => c.name.includes(value) || c.description.includes(value));

    if (inputTag.value !== '') {
        filtered = filtered.filter(c => c.tags.includes(inputTag.value))
    }

    displayCoupons(filtered);
}


function loadMore(coupons_to_display) {
    let element = document.getElementsByClassName('coupon_list')[0];
    let insertion = '';
    for (let i = 0; i < coupons_to_display.length; i++) {
        insertion +=
            '<div class="coupon_card">\n' +
            '            <div class="image_part">\n' +
            '                <img src="' + coupons_to_display[i].img + '" alt="' + coupons_to_display[i].name + '">\n' +
            '            </div>\n' +
            '            <div class="description_part">\n' +
            '                <div class="coupon_name">\n' +
            '                    ' + coupons_to_display[i].name + '\n' +
            '                </div>\n' +
            '                <div class="icon">\n' +
            '                    <i class="material-icons">\n' +
            '                        favorite\n' +
            '                    </i>\n' +
            '                </div>\n' +
            '                <div class="brief_description">\n' +
            '                    ' + coupons_to_display[i].description + '\n' +
            '                </div>\n' +
            '                <div class="expires_in">\n' +
            '                    Expires in 3 days\n' +
            '                </div>\n' +
            '            </div>\n' +
            '            <div class="line"></div>\n' +
            '            <div class="price_part">\n' +
            '                <div class="price">\n' +
            '                    $' + coupons_to_display[i].price + '\n' +
            '                </div>\n' +
            '                <div class="add_to_card">\n' +
            '                    <button>Add to Cart</button>\n' +
            '                </div>\n' +
            '            </div>\n' +
            '        </div>';
    }
    element.innerHTML += insertion;
}

window.addEventListener('scroll', () => {
    let scrollable = document.documentElement.scrollHeight - window.innerHeight;
    let scrolled = window.scrollY;

    if (scrollable <= scrolled + 1) {
        loadMore(coupon_list);
    }
})

