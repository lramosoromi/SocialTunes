/**
 * Created by skylight on 19/02/2015.
 */

function setSongTitle (songTitle) {
    $.ajax({
        dataType: 'json',
        type: 'POST',
        url: 'http://localhost:8080/SetSongAttributeServlet',
        data: { songTitle: songTitle }
    });
}
