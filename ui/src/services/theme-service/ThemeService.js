class ThemeService {
    themeKey;
    bodyElement;
    currentTheme;

    constructor() {
        this.themeKey = "bs-theme";
        this.bodyElement = document.body;
        this.currentTheme = "";

        // Initialize the theme based on the value in local storage
        const storedTheme = localStorage.getItem(this.themeKey);
        if (storedTheme) {
            this.bodyElement.setAttribute("data-bs-theme", storedTheme);
            this.currentTheme = storedTheme;
        } else {
            this.bodyElement.removeAttribute("data-bs-theme");
            this.currentTheme = "light"; // Default to light theme if no stored value
        }
    }

    toggleTheme() {
        if (this.bodyElement.getAttribute("data-bs-theme")) {
            this.bodyElement.removeAttribute("data-bs-theme");
            localStorage.removeItem(this.themeKey);
            return this.currentTheme = "light";
        } else {
            this.bodyElement.setAttribute("data-bs-theme", "dark");
            localStorage.setItem(this.themeKey, "dark");
            return this.currentTheme = "dark";
        }
    }

    getTheme() {
        return this.currentTheme;
    }
}

const themeService = new ThemeService();
export default themeService;