// checks the URL for tab identifiers, if it finds one, it will show the tab.
document.addEventListener("DOMContentLoaded", function() {
let hash = window.location.hash;
if(hash) {
    let tabPane = document.querySelector(hash);
    let tabLink = document.querySelector(`[href="${hash}"]`);

    if (tabPane && tabLink) {
        let tab = new bootstrap.Tab(tabLink);
        tab.show();
    }
}
});