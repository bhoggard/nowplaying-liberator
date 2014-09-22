function getCounterstream() {
  $.get("/counterstream", renderCountstream);
}

function renderCountstream(data) {
  renderSection('#counterstream', data);
}

function renderSection(id, data) {
  $(id.concat(' .title')).text(data.title);
  $(id.concat(' .composer')).text(data.composer);
}

function renderPieces() {
  getCounterstream();
}

$(renderPieces());
