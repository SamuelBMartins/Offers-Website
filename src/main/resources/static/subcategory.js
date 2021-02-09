function updateaaa(input){
    console.log(input.value);

    var context = document.querySelector('base').getAttribute('href');
    var url = context + "category/search?q="+input.value;
    var options = {method : "GET"};
    fetch(url, options)
        .then(function(response){
            if (!response.ok)
                throw new Error("HTTP error:" + response.status);
            return response.json();
        })
        .then(function(elements){
            var container = document.getElementById("categoria");
            var pars = "";
            pars += "<label for=\"category\">Sottocategria:</label>\n";
            pars += "<select class=\"form-control\" id=\"category\" name=\"category\">\n" +
                "                <option value=\"\" hidden=\"\">Scegli qui</option>\n"

            for (var i = 0; i < elements.length; i++) {
                pars += "<option value=\""+elements[i]+"\">"+elements[i]+"</option>\n";
            }

            pars += "</select>";
            container.innerHTML = pars;





        });

}