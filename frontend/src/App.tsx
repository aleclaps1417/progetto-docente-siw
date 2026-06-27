import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Layout from './components/Layout';
import TorneiList from './components/TorneiList';
import CalendarioPartite from './components/CalendarioPartite';
import GiocatoriList from './components/GiocatoriList';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Il Layout fa da "guscio": navbar fissa + Outlet che cambia */}
        <Route path="/" element={<Layout />}>
          {/* Rotte figlie: vengono inserite nell'Outlet del Layout */}
          <Route index element={<TorneiList />} />
          <Route path="torneo/:torneoId/calendario" element={<CalendarioPartite />} />
          <Route path="giocatori" element={<GiocatoriList />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;