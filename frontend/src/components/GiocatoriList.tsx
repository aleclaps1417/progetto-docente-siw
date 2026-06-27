import { useState, useEffect } from 'react';
import {
  Box, Typography, Table, TableBody, TableCell, TableContainer,
  TableHead, TableRow, Paper, TextField, FormControl, InputLabel,
  Select, MenuItem, CircularProgress, Alert, InputAdornment, Chip
} from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';
import PersonIcon from '@mui/icons-material/Person';
import type { SelectChangeEvent } from '@mui/material';
import type { Giocatore } from '../types';
import { getGiocatori } from '../services/giocatoreService';

export default function GiocatoriList() {
  // Stati per i dati
  const [giocatori, setGiocatori] = useState<Giocatore[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  
  // Stati per i filtri
  const [ricerca, setRicerca] = useState<string>('');
  const [squadraFiltro, setSquadraFiltro] = useState<string>('TUTTE');

  // Caricamento dati
  useEffect(() => {
    const carica = async () => {
      try {
        const dati = await getGiocatori();
        setGiocatori(dati);
      } catch (err) {
        setError(err instanceof Error ? err.message : "Errore sconosciuto");
      } finally {
        setLoading(false);
      }
    };
    carica();
  }, []);

  // Lista squadre uniche dal dataset (per popolare il dropdown)
  // Usiamo Map per gestire id+nome insieme, evitando duplicati per ID
  const squadreMap = new Map<number, string>();
  giocatori.forEach(g => {
    if (g.squadraId !== null && g.squadra !== null) {
      squadreMap.set(g.squadraId, g.squadra);
    }
  });
  const squadreUniche = Array.from(squadreMap.entries())
    .map(([id, nome]) => ({ id, nome }))
    .sort((a, b) => a.nome.localeCompare(b.nome));

  // Logica di filtraggio combinata
  const giocatoriFiltrati = giocatori
    // Filtro 1: ricerca live su nome E cognome (case-insensitive)
    .filter(g => {
      if (!ricerca.trim()) return true;
      const queryLower = ricerca.toLowerCase().trim();
      return g.nome.toLowerCase().includes(queryLower) ||
             g.cognome.toLowerCase().includes(queryLower) ||
             `${g.nome} ${g.cognome}`.toLowerCase().includes(queryLower);
    })
    // Filtro 2: squadra
    .filter(g => {
      if (squadraFiltro === 'TUTTE') return true;
      if (squadraFiltro === 'SENZA_SQUADRA') return g.squadraId === null;
      return g.squadraId === parseInt(squadraFiltro);
    });

  if (loading) return <CircularProgress sx={{ display: 'block', margin: '20px auto' }} />;
  if (error) return <Alert severity="error">{error}</Alert>;

  return (
    <Box sx={{ 
      p: 3, 
      backgroundColor: 'white', 
      borderRadius: 2, 
      boxShadow: 3,
      width: '100%',
      maxWidth: '1000px',
      margin: '0 auto'
    }}>
      <Typography variant="h5" sx={{ fontWeight: 'bold', mb: 3, display: 'flex', alignItems: 'center' }}>
        <PersonIcon sx={{ mr: 1, color: '#3498db' }} />
        Gestione Giocatori
      </Typography>

      {/* AREA FILTRI */}
      <Box sx={{
        mb: 3,
        p: 2,
        backgroundColor: '#f8f9fa',
        borderRadius: 2,
        border: '1px solid #e0e0e0',
        display: 'flex',
        flexWrap: 'wrap',
        gap: 2,
        alignItems: 'center'
      }}>
        {/* Barra di ricerca live */}
        <TextField
  placeholder="Cerca per nome o cognome..."
  variant="outlined"
  size="small"
  value={ricerca}
  onChange={(e) => setRicerca(e.target.value)}
  sx={{ flexGrow: 1, minWidth: 250, backgroundColor: 'white' }}
  slotProps={{
    input: {
      startAdornment: (
        <InputAdornment position="start">
          <SearchIcon color="action" />
        </InputAdornment>
      ),
    },
  }}
/>

        {/* Dropdown squadra */}
        <FormControl size="small" sx={{ minWidth: 220, backgroundColor: 'white' }}>
          <InputLabel>Squadra</InputLabel>
          <Select
            value={squadraFiltro}
            label="Squadra"
            onChange={(e: SelectChangeEvent) => setSquadraFiltro(e.target.value)}
          >
            <MenuItem value="TUTTE">Tutte le squadre</MenuItem>
            <MenuItem value="SENZA_SQUADRA">⚠️ Senza squadra</MenuItem>
            {squadreUniche.map(s => (
              <MenuItem key={s.id} value={s.id.toString()}>{s.nome}</MenuItem>
            ))}
          </Select>
        </FormControl>
      </Box>

      {/* CONTEGGIO RISULTATI */}
      <Typography variant="body2" sx={{ mb: 2, color: 'text.secondary' }}>
        Giocatori trovati: <strong style={{ color: 'black' }}>{giocatoriFiltrati.length}</strong> su {giocatori.length}
      </Typography>

      {/* TABELLA */}
      {giocatoriFiltrati.length > 0 ? (
        <TableContainer component={Paper} variant="outlined">
          <Table>
            <TableHead sx={{ backgroundColor: '#3498db' }}>
              <TableRow>
                <TableCell sx={{ color: 'white', fontWeight: 'bold' }}>Cognome</TableCell>
                <TableCell sx={{ color: 'white', fontWeight: 'bold' }}>Nome</TableCell>
                <TableCell sx={{ color: 'white', fontWeight: 'bold' }}>Ruolo</TableCell>
                <TableCell sx={{ color: 'white', fontWeight: 'bold' }}>Squadra</TableCell>
                <TableCell sx={{ color: 'white', fontWeight: 'bold' }} align="right">Altezza</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {giocatoriFiltrati.map(g => (
                <TableRow key={g.id} hover>
                  <TableCell sx={{ fontWeight: 'bold' }}>{g.cognome}</TableCell>
                  <TableCell>{g.nome}</TableCell>
                  <TableCell>
                    {g.ruolo ? <Chip label={g.ruolo} size="small" variant="outlined" /> : '-'}
                  </TableCell>
                  <TableCell>
                    {g.squadra || <span style={{ color: '#999', fontStyle: 'italic' }}>Nessuna</span>}
                  </TableCell>
                  <TableCell align="right">
                    {g.altezza ? `${g.altezza} cm` : '-'}
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      ) : (
        <Alert severity="info">
          Nessun giocatore trovato con i filtri selezionati.
        </Alert>
      )}
    </Box>
  );
}