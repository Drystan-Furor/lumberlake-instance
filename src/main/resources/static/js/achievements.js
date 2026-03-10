arts_ict_page.main.renderAchievements = (achievements, containerId) => {
    const prestatiesContainer = document.getElementById(containerId);
    prestatiesContainer.innerHTML = '';

    achievements.forEach(prestatie => {
        const itemDiv = document.createElement('div');
        itemDiv.className = "flex gap-2";

        const svgIcon = document.createElement('i');
        svgIcon.className = "w-5 h-5 text-yellow-300 dark:text-yellow-300 fas fa-star";

        const spanText = document.createElement('span');
        spanText.className = "text-slate-800 text-sm";
        spanText.textContent = prestatie;

        itemDiv.appendChild(svgIcon);
        itemDiv.appendChild(spanText);

        prestatiesContainer.appendChild(itemDiv);
    });
}

arts_ict_page.list.emma = () => {
    arts_ict_page.main.renderAchievements(
        [
            "VP 2x ’14",
            "U2 3x ’15",
            "U1 12x",
            "BOB 12x",
            "CAC 10x",
            "CACIB 6x",
            "Res CAC 7x",
            "BOS 10x",
            "LU JK ’15",
            "RASGROEP 3 2x",
            "Res BIS 1x",
            "BIS 1x",
            "Beste Zelfgefokte Hond ’15 2x",
            "CW 1x",
            "Dog of the Year Show ’16",
            "1e plaats PL RASGROEP 3"
        ],
        'prestaties-list-emma'
    );
}

arts_ict_page.list.king = () => {
    arts_ict_page.main.renderAchievements(
        [
            "VB1 1x",
            "VB2 4x",
            "ZG 1x",
            "U1 9x",
            "BESTE JEUGDREU 2x",
            "BESTE REU 1x",
            "BOB 2x",
            "DUITS JEUGDKAMPIOEN '24",
            "LUXEMBURGS JEUGDKAMPIOEN '24",
            "Crufts Qualificatie"
        ],
        'prestaties-list-king'
    );
}

arts_ict_page.list.sofietje = () => {
    arts_ict_page.main.renderAchievements(
        [
            "VB4",
            "VB4",
            "ZG 2x",
            "ZG2",
            "U3",
            "U2",
            "ZG1",
            "U1 2x"
        ],
        'prestaties-list-sofietje'
    );
}
