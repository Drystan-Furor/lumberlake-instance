'use strict';
window.arts_ict_page = window.arts_ict_page || {};
arts_ict_page = {
    main: {},
    list: {}
}

arts_ict_page.main.initApp = () => {

    arts_ict_page.main.menuButtonFunctions();
    arts_ict_page.main.themeToggle();

    const el = document.getElementById('some-element-id');
    if (el) {
        el.innerText = arts_ict_page.main.getWeeksSince("13.5.25");
    }

    arts_ict_page.list.emma();
    arts_ict_page.list.king();
    arts_ict_page.list.sofietje();

}

arts_ict_page.main.menuButtonFunctions = () => {
    const hamburgerBtn = document.getElementById('hamburger-button');
    const mobileMenu = document.getElementById('mobile-menu');

    const toggleMenu = () => {
        mobileMenu.classList.toggle('hidden');
        mobileMenu.classList.toggle('flex');
        hamburgerBtn.classList.toggle('toggle-btn');
        const expanded = hamburgerBtn.classList.contains('toggle-btn');
        hamburgerBtn.setAttribute('aria-expanded', expanded);
    }

    hamburgerBtn.addEventListener('click', toggleMenu);
    mobileMenu.addEventListener('click', toggleMenu);
}

arts_ict_page.main.themeToggle = function () {
    // Function to toggle the dark-theme class
    function toggleDarkTheme(shouldAdd) {
        document.body.classList.toggle('dark-theme', shouldAdd);
    }

    // Initial check for dark mode preference
    toggleDarkTheme(window.matchMedia('(prefers-color-scheme: dark)').matches);

    // Listener for changes in the system's dark mode preference
    window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', event => {
        toggleDarkTheme(event.matches);
    });
}

/**
 * getWeeksSince("13.5.25") ➜ "4,5 weeks"   // if today were 14 Jun 2025
 *
 * Accepts a date string in d.M.yy or d.M.yyyy and
 * returns the time since then, rounded to one decimal,
 * formatted with a comma decimal-separator.
 */
arts_ict_page.main.getWeeksSince = function(dottedDate) {
    // 1. Split "13.5.25" ⇒ ["13","5","25"]
    const [d, m, y] = dottedDate.split('.').map(Number);

    // 2. Expand 2-digit year to 2000-based (25 ⇒ 2025).
    const fullYear = y < 100 ? 2000 + y : y;

    // 3. Build the Date object (months are 0-indexed in JS)
    const then = new Date(fullYear, m - 1, d);

    // 4. Difference in milliseconds ⇒ days ⇒ weeks
    const now = new Date();
    const msInDay = 86_400_000;
    const days = (now - then) / msInDay;
    const weeks = days / 7;

    // 5. One decimal, replace "." with "," for Dutch-style
    return `${weeks.toFixed(1).replace('.', ',')} weken`;
}

document.addEventListener('DOMContentLoaded', arts_ict_page.main.initApp);
