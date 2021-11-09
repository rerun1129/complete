var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        })

        $('#btn-update').on('click', function () {
            _this.update();
        })

        $('#btn-delete').on('click', function () {
            _this.delete();
        })

    },
    save : function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        }

        $.ajax({

            type: 'POST',           //POST 방식으로 서버로 보냄
            url: '/api/posts',   //컨트롤러 save 메서드 주소
            dataType: 'json',       //타입은 json
            contentType: 'application/json; charset=utf-8',     //컨텐츠 타입은 이런식
            data: JSON.stringify(data)                          //json 데이터를 문자열화 시킴

        }).done(function () {
            alert('글이 등록되었습니다.')
            window.location.href = '/';             //done이면 홈으로
        }).fail(function (error) {
            alert(JSON.stringify(error));           //실패하면 에러 메세지
        })
    },

    update : function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        }
        var id = $('#id').val()

        $.ajax({
            type: 'PUT',
            url: '/api/posts/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 수정되었습니다.')
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error))
        })

    },

    delete : function () {
        var id = $('#id').val()

        $.ajax({
            type: 'DELETE',
            url: '/api/posts/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function () {
            alert('글이 삭제되었습니다.')
            window.location.href='/'
        }).fail(function (error) {
            alert(JSON.stringify(error))
        })
    }

}

main.init();