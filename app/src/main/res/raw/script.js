// The authors disclaim copyright to this source code


function cb(id, event, err, data) {
    let d = document.getElementById("txt");

    let r = window.Android.cb(id, event, err, data);
    if (r.startsWith("add:")) {
        d.innerText = d.innerText + r.substr(4, r.indexOf(';') - 4);
    }
    console.log(r);
    return true;
}