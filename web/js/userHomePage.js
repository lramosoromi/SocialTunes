/**
 * Created by skylight on 21/07/14.
 */
var tracks = [];
var addIndex = 0;
var trackCount = 0;
var reloadPage = false;
var audio = new Audio();

$(document).ready(function() {
    init();
});

function init(){
    var supportsAudio = !!document.createElement('audio').canPlayType;
    if(supportsAudio) {
        var playing = false, index = 0;
        var npAction = $('#npAction'),
            npTitle = $('#npTitle'),
            audio = $('#audio1').bind('play', function() {
                playing = true;
                npAction.text('Now Playing:');
                addReproduction(tracks[index]);
            }).bind('pause', function() {
                playing = false;
                npAction.text('Paused:');
            }).bind('ended', function() {
                npAction.text('Paused:');
                if((index + 1) < trackCount) {
                    index++;
                    loadTrack(index);
                    audio.play();
                    addReproduction(tracks[index]);
                } else {
                    audio.pause();
                    index = 0;
                    loadTrack(index);
                }
            }).get(0),
            btnPrev = $('#btnPrev').click(function() {
                if((index - 1) > -1) {
                    index--;
                    loadTrack(index);
                    if(playing) {
                        audio.play();
                        addReproduction(tracks[index]);
                    }
                } else {
                    audio.pause();
                    index = 0;
                    loadTrack(index);
                }
            }),
            btnNext = $('#btnNext').click(function() {
                if((index + 1) < trackCount) {
                    index++;
                    loadTrack(index);
                    if(playing) {
                        audio.play();
                        addReproduction(tracks[index]);
                    }
                } else {
                    audio.pause();
                    index = 0;
                    loadTrack(index);
                }
            }),
            li = $('#plUL').find('li').click(function() {
                var id = parseInt($(this).index());
                if(id !== index) {
                    playTrack(id);
                    addReproduction(tracks[id]);
                }
            }),
            erasePlaylist = $('#btnErase').click(function() {
                tracks = [];
                addIndex = 0;
                trackCount = tracks.length;
                var lis = document.querySelectorAll('#plUL li');
                for(var i=0; li=lis[i]; i++) {
                    li.parentNode.removeChild(li);
                }
                audio.src = "";
                npTitle.text("");
                npAction.text('Paused:');
            }),
            loadTrack = function(id) {
                $('.plSel').removeClass('plSel');
                $('#plUL').find('li:eq(' + id + ')').addClass('plSel');
                npTitle.text(tracks[id]);
                index = id;
                setSong(tracks[id]);
                audio.src = "http://localhost:8080/PlaySongServlet";
            },
            playTrack = function(id) {
                loadTrack(id);
                audio.play();
            };
        if(audio.canPlayType('audio/ogg')) {
            extension = '.ogg';
        }
        if(audio.canPlayType('audio/mpeg')) {
            extension = '.mp3';
        }
        if(reloadPage){
            loadTrack(index);
        }
        reloadPage = false;
    }
}
function setSong(parameter) {
    $.ajax({
        dataType: 'json',
        type: 'POST',
        url: 'http://localhost:8080/PlaySongServlet',
        data: { songTitle: parameter }
    });
}
function addSong(song) {
    var exists = false;
    for (var i = 0; i < tracks.length; i++){
        if (tracks[i] != null && tracks[i].localeCompare(song) == 0){
            exists = true;
        }
    }
    if (!exists){
        var ul = document.getElementById("plUL");
        var li = document.createElement("li");
        var a = document.createElement("a");
        a.setAttribute("id", song);
        a.setAttribute("href", "#");
        a.setAttribute("onclick", "setSong(this.id)");
        a.appendChild(document.createTextNode(song));
        li.appendChild(a);
        ul.appendChild(li);
        tracks[addIndex] = song;
        addIndex ++;
        trackCount = tracks.length;
        reloadPage = true;
        init()
    }
}
function savePlaylist() {
    var i;
    var queryString = "";
    var length = tracks.length;
    for ( i = 0; i < length; i ++){
        queryString += tracks[i] + ",";
    }
    queryString = queryString.substring(0,queryString.length -1);

    $.ajax({
        dataType: 'json',
        type: 'POST',
        url: 'http://localhost:8080/SetPlaylistAttributeServlet',
        data: { songs: queryString }
    });
    window.location.replace("http://localhost:8080/savePlaylist.jsp");
    audio.src = "";
}
function loadPlaylist(playlistName) {
    tracks = [];
    if (audio != null){
        audio.src = "";
    }
    addIndex = 0;
    $.get(
        'LoadPlaylistServlet',
        { playlistName: playlistName },
        function(responseJson) {
            var lis = document.querySelectorAll('#plUL li');
            for(var i=0; li=lis[i]; i++) {
                li.parentNode.removeChild(li);
            }
            $.each(responseJson, function(index, song) { // Iterate over the JSON array.
                addSong(song);
            });
        }
    );
}
function addReproduction(songName){
    if (audio.currentTime == 0){
        $.ajax({
            dataType: 'json',
            type: 'POST',
            url: 'http://localhost:8080/AddReproductionServlet',
            data: { songTitle: songName }
        });
    }
}