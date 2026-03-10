arts_ict_page.main.bucket = () => {
    const shuffleArray = (array) => {
        for (let i = array.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));
            [array[i], array[j]] = [array[j], array[i]]; // Swap elements
        }
        return array;
    };

    const maxImg = 32
    const imageList = [];
    for (let i = 1; i <= maxImg; i++) {
        imageList.push(`${i}.avif`);
    }

    const shuffledImageList = shuffleArray([...imageList]);
    const randomImages = shuffledImageList.slice(0, maxImg);
    const container = document.querySelector(".bucket");

    const createImage = (imageName) => {
        const div = document.createElement("div");
        const img = document.createElement("img");

        // // Tailwind classes voor sizing & looks
        div.className = "w-full max-w-4xl mx-auto mb-10";
        img.className = "w-full h-auto rounded-lg shadow-2xl";

        img.setAttribute("loading", "lazy");
        img.setAttribute("decoding", "async");
        img.setAttribute("alt", "Jack Russell Terriër van Lumberlake kennel");

        container.appendChild(div);
        img.setAttribute("src", `img/photobook/${imageName}`);
        div.appendChild(img);

        gsap.set(div, { scale: 0 });
        gsap.set(img, { opacity: 1, filter: "contrast(5)"});

        gsap.to(div, {
            scrollTrigger: {
                trigger: div,
                start: "top bottom",
                end: "top 50%",
                scrub: true
            },
            scale: 1,
            ease: Back.easeOut.config(2)
        });
        gsap.to(img, {
            scrollTrigger: {
                trigger: div,
                start: "top bottom",
                end: "top 50%",
                scrub: true
            },
            opacity: 1,
            filter: "contrast(1)"
        });
    };

    for (let i = 0; i < 1; i++) {
        randomImages.forEach((imageName, index) => {
            createImage(imageName);
        });
    }

    ScrollTrigger.create({
        trigger: document.body,
        start: "top top",
        end: "bottom bottom",
        onUpdate: (self) => {
            let progress = self.progress.toFixed(2);
            if (progress >= 0.9 && self.direction === 1) {
                imageList.forEach((imageName, index) => {
                    createImage(imageName);
                });
                ScrollTrigger.refresh();
            }
        }
    })
}

document.addEventListener('DOMContentLoaded', arts_ict_page.main.bucket);

