const moduleRoute = document.querySelector("#modules-root");
const modules = moduleRoute.querySelectorAll("[data-modules]")
const smodules = Array.prototype.slice.call(moduleRoute.querySelectorAll("[data-smodules]"))
const adminCard = moduleRoute.querySelector("#admin-card")
const children = Array.prototype.slice.call(modules);

/**
for (const child of children) {
    const moduleName = child.querySelector("[data-module]")
    moduleName.addEventListener("click", () => {
        const smodules = child.querySelector("[data-smodules]");
        if(smodules.classList.contains("hidden"))  {
            smodules.classList.remove("hidden");
        } else {
            smodules.classList.add("hidden");
        }
    })
}*/
