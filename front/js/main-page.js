let coupons = JSON.parse(window.localStorage.getItem('coupons'));
let tags = JSON.parse(window.localStorage.getItem('tags'));

let filtered_coupons = [...coupons];

let pageNumber = 0;
let pageSize = 4;

document.addEventListener("DOMContentLoaded", function (event) {
    displayTags();
    displayCoupons(filtered_coupons.slice(0, pageSize));
});

function addTag(i, element) {
    let newChild = document.createElement("div");
    newChild.className = "category_item";
    newChild.style.visibility = 'hidden';
    newChild.innerHTML =
        '        <img src="https://www.beautycolorcode.com/ffffff-1280x1024.png" alt="Category">\n' +
        '        <span>' + tags[i] + '</span>\n';
    element.appendChild(newChild);
    return newChild;
}

function displayTags() {
    let element = document.getElementsByClassName('category_list')[0];
    element.innerHTML = '';
    let categoriesRoot = document.getElementById('category');
    let insertionInput = '<option value="" selected>All categories</option>'
    for (let i = 0; i < tags.length; i++) {
        insertionInput +=
            '<option value="' + tags[i] + '">' + tags[i] + '</option>';
    }
    categoriesRoot.innerHTML = insertionInput;

    const timeDiff = 200;
    let time = 200;
    for (let i = 0; i < tags.length; i++) {
        let appended = addTag(i, element);
        setTimeout(() => {
            appended.style.visibility = 'visible'
            appended.style.animationName = 'img-ani'
            appended.style.animationDuration = '2s'
            appended.style.animationTimingFunction = 'ease-in'
        }, time);
        time = time + timeDiff;
    }
}

function insertionCoupon(coupons_to_display, i, idImage) {
    return '<div class="coupon_card">\n' +
        '            <div class="image_part">\n' +
        '                <img src="' + coupons_to_display[i].img + '" id="' + idImage + '" alt="' + coupons_to_display[i].name + '">\n' +
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
        '            </div>\n' +
        '                <div class="brief_description">\n' +
        '                    ' + coupons_to_display[i].description + '\n' +
        '                </div>\n' +
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

function insertionCouponAppend(coupon, idImage) {
    return '         <div class="image_part">\n' +
        '                <img src="' + coupon.img + '" id="' + idImage + '" alt="' + coupon.name + '">\n' +
        '            </div>\n' +
        '            <div class="description_part">\n' +
        '                <div class="coupon_name">\n' +
        '                    ' + coupon.name + '\n' +
        '                </div>\n' +
        '                <div class="icon">\n' +
        '                    <i class="material-icons">\n' +
        '                        favorite\n' +
        '                    </i>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '                <div class="brief_description">\n' +
        '                    ' + coupon.description + '\n' +
        '                </div>\n' +
        '            <div class="line"></div>\n' +
        '            <div class="price_part">\n' +
        '                <div class="price">\n' +
        '                    $' + coupon.price + '\n' +
        '                </div>\n' +
        '                <div class="add_to_card">\n' +
        '                    <button>Add to Cart</button>\n' +
        '                </div>\n' +
        '            </div>';
}

function displayCoupons(coupons_to_display) {
    let element = document.getElementsByClassName('coupon_list')[0];
    element.innerHTML = '';
    loadMore(coupons_to_display);
}

function filter() {
    pageNumber = 0;
    let inputName = document.getElementById('search');
    let inputTag = document.getElementById('category');

    let value = inputName.value;
    let filtered = coupons
        .filter(c => c.name.includes(value) || c.description.includes(value));

    if (inputTag.value !== '') {
        filtered = filtered.filter(c => c.tags.includes(inputTag.value))
    }

    if (JSON.stringify(filtered) !== JSON.stringify(filtered_coupons)) {
        filtered_coupons = [...filtered];
        displayCoupons(filtered_coupons.slice(0, pageSize));
    }
}

function loadMore(coupons_to_display) {
    pageNumber++;
    const timeDiff = 200;
    let time = 200;
    for (let i = 0; i < coupons_to_display.length; i++) {
        let appended = addCoupon(coupons_to_display[i], i);
        setTimeout(() => {
            appended.style.visibility = 'visible'
            appended.style.animationName = 'img-ani'
            appended.style.animationDuration = '2s'
            appended.style.animationTimingFunction = 'ease-in'
        }, time);
        time = time + timeDiff;
    }
}


function addCoupon(coupon, i) {
    let element = document.getElementsByClassName('coupon_list')[0];
    let newChild = document.createElement("div");
    newChild.className = "coupon_card";
    newChild.style.visibility = 'hidden';
    newChild.innerHTML =
        insertionCouponAppend(coupon, 'coupon_' + i + '_' + pageNumber);
    element.appendChild(newChild);
    return newChild;
}

window.addEventListener('scroll', () => {
    let scrollable = document.documentElement.scrollHeight - window.innerHeight;
    let scrolled = window.scrollY;

    if (scrollable <= scrolled + 1) {
        // loadMore(coupon_list)
        handleLoadMore();
    }
})


function throttle(callback, wait, immediate = false) {
    let timeout = null
    let initialCall = true

    return function () {
        const callNow = immediate && initialCall
        const next = () => {
            callback.apply(this, arguments)
            timeout = null
        }

        if (callNow) {
            initialCall = false
            next()
        }

        if (!timeout) {
            timeout = setTimeout(next, wait)
        }
    }
}

const handleLoadMore = throttle(() => {
    if (pageNumber * pageSize < filtered_coupons.length) {
        let start = pageNumber * pageSize;
        loadMore(filtered_coupons.slice(start, start + pageSize))
    }
}, 2000)

const handleType = throttle(() => {
    filter()
}, 1000)

