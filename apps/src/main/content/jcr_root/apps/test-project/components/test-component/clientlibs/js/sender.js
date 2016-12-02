
     var send = function(e){
        e.preventDefault();
        e.stopPropagation();
        var name = $('input#inputName').val();
        var email = $('input#inputEmail').val();
        var response = getAnswer(name, email);


                    //console.log(name);
                    //console.log(email);
     }
    var getAnswer = function (name , email) {
        var URI = "/content/test-project/bin/testResorceType";
        $.get( URI, {'name':name, 'email':email})
        .done(function(data) {
            console.log("done: ", data);
             showAnswer(data);
        })
        .fail(function(data) {
            console.err("failed response to  ", URI);

        });
    }
    var showAnswer = function (response) {
            var $answer = $('.answer');
            var $responseFirstName = $('.answer .firstName .text').text(response.firstName);
            var $responseLastName = $('.answer .lastName .text').text(response.lastName);
            var $responseEmail = $('.answer .address .text').text(response.address);

            $answer.show();
        }