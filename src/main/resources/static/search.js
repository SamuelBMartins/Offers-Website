function search(input){
    if (input.value.length < 3 && input.value.length > 0)
        return;

    var context = document.querySelector('base').getAttribute('href');
    var url = context + "item/search?q="+input.value;
    var options = {method : "GET"};
    fetch(url, options)
        .then(function(response){
            if (!response.ok)
                throw new Error("HTTP error:" + response.status);
            return response.json();
        })
        .then(function(elements){
            var container = document.getElementById("main");
            var offers = [];
            var request = [];
            var pars="";
            for (var i = 0; i < elements.length; i++) {
                if (elements[i].type === "OFFER")
                    offers.push(elements[i])
                else
                    request.push(elements[i])
            }

            if (offers.length > 0) {
                pars += "<h2>Offerte</h2>";
                pars += "<div class=\"row\">";

                for (var i = 0; i < offers.length; i++) {
                    var mydate = new Date(offers[i].date);
                    pars+="<div class=\"col-md-4\">\n" +
                        "            <div class=\"card mycard mb-4 shadow-sm\">\n" +
                        "                <a href=\""+context+"item/"+offers[i].id+"\">\n" +
                        "                    <img class=\"bd-placeholder-img card-img-top\" width=\"100%\" height=\"225\" src=\""+context+"item/"+offers[i].id+"/image\" preserveAspectRatio=\"xMidYMid slice\" focusable=\"false\" role=\"img\" aria-label=\"Placeholder: Thumbnail\">\n" +
                        "                </a>\n" +
                        "                <div class=\"card-body\">\n" +
                        "                    <a class=\"title-link\" href=\""+context+"item/"+offers[i].id+"\">\n" +
                        "                        <h3 class=\"mb-0\">"+offers[i].title.substring(0,38)+"</h3>\n" +
                        "                    </a>\n" +
                        "                    <p class=\"card-text\">"+offers[i].description.substring(0,100)+"</p>\n" +
                        "                    <div class=\"d-flex justify-content-between align-items-center\">\n" +
                        "                        <div class=\"btn-group\">\n" +
                        "                            <button type=\"button\" class=\"btn btn-sm btn-outline-secondary\" onclick=\"location.href='"+context+"item/"+offers[i].id+"'\">Leggi</button>\n" +
                        "                        </div>\n" +
                        "                        <small class=\"text-muted\"><time datetime=\""+mydate+"\">"+mydate.toLocaleString()+"</time></small>\n" +
                        "                    </div>\n" +
                        "                </div>\n" +
                        "            </div>\n" +
                        "        </div>";
                }

                pars += "</div>";
            }

            if (request.length > 0) {
                pars += "<h2>Richieste</h2>";
                pars += "<div class=\"row\">";

                for (var i = 0; i < request.length; i++) {
                    var mydate = new Date(request[i].date);
                    pars+="<div class=\"col-md-4\">\n" +
                        "            <div class=\"card mycard mb-4 shadow-sm\">\n" +
                        "                <a href=\""+context+"item/"+request[i].id+"\">\n" +
                        "                    <img class=\"bd-placeholder-img card-img-top\" width=\"100%\" height=\"225\" src=\""+context+"item/"+request[i].id+"/image\" preserveAspectRatio=\"xMidYMid slice\" focusable=\"false\" role=\"img\" aria-label=\"Placeholder: Thumbnail\">\n" +
                        "                </a>\n" +
                        "                <div class=\"card-body\">\n" +
                        "                    <a class=\"title-link\" href=\""+context+"item/"+request[i].id+"\">\n" +
                        "                        <h3 class=\"mb-0\">"+request[i].title.substring(0,38)+"</h3>\n" +
                        "                    </a>\n" +
                        "                    <p class=\"card-text\">"+request[i].description.substring(0,100)+"</p>\n" +
                        "                    <div class=\"d-flex justify-content-between align-items-center\">\n" +
                        "                        <div class=\"btn-group\">\n" +
                        "                            <button type=\"button\" class=\"btn btn-sm btn-outline-secondary\" onclick=\"location.href='"+context+"item/"+request[i].id+"'\">Leggi</button>\n" +
                        "                        </div>\n" +
                        "                        <small class=\"text-muted\"><time datetime=\""+mydate+"\">"+mydate.toLocaleString()+"</time></small>\n" +
                        "                    </div>\n" +
                        "                </div>\n" +
                        "            </div>\n" +
                        "        </div>";
                }

                pars += "</div>";
            }


            container.innerHTML = pars;
        })
}