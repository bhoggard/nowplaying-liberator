function getCounterstream() {
  $.get("/counterstream", renderCounterstream);
}

function renderCounterstream(data) {
  renderSection('#counterstream', data);
}

function getEarwaves() {
  $.get("/earwaves", renderEarwaves);
}

function renderEarwaves(data) {
  renderSection('#earwaves', data);
}

function getQ2() {
  $.get("/q2", renderQ2);
}

function renderQ2(data) {
  renderSection('#q2', data);
}

function getYLE() {
  $.get("/yle", renderYLE);
}

function renderYLE(data) {
  renderSection('#yle', data);
}

function getSecondInversion() {
  $.get("/second-inversion", renderSecondInversion);
}

function renderSecondInversion(data) {
  renderSection('#second-inversion', data);
}

function renderSection(id, data) {
  $(id.concat(' .title')).text(data.title);
  $(id.concat(' .composer')).text(data.composer);
}

function renderPieces() {
  getCounterstream();
  getEarwaves();
  getQ2();
  getSecondInversion();
  getYLE();
}

function start() {
  renderPieces();
  setInterval(renderPieces, 60 * 1000);
}

$(start());
