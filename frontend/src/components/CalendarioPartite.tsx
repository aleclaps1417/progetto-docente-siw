import { useState, useEffect } from 'react';
import { 
  Box, Typography, Table, TableBody, TableCell, TableContainer, 
  TableHead, TableRow, Paper, ToggleButton, ToggleButtonGroup,
  FormControl, InputLabel, Select, MenuItem, CircularProgress, 
  Alert, Chip
} from '@mui/material';
import type { SelectChangeEvent } from '@mui/material';
import { useParams } from 'react-router-dom';
import type { Partita } from '../types';
import { getPartiteByTorneo } from '../services/partitaService';




type FiltroStato = 'TUTTE' | 'SCHEDULED' | 'PLAYED' | 'CANCELLED';
type OrdineData = 'ASC' | 'DESC';

export default function CalendarioPartite() {
  const { torneoId } = useParams();
  // Stati per i dati
  const [partite, setPartite] = useState<Partita[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  
  // Stati per i filtri
  const [filtroStato, setFiltroStato] = useState<FiltroStato>('TUTTE');
  const [filtroSquadra, setFiltroSquadra] = useState<string>('TUTTE');
  const [ordineData, setOrdineData] = useState<OrdineData>('ASC');

  // Caricamento dati dal backend
  useEffect(() => {
    const caricaPartite = async () => {
      try {
        const dati = await getPartiteByTorneo(Number(torneoId));
        setPartite(dati);
      } catch (err) {
        setError(err instanceof Error ? err.message : "Errore sconosciuto");
      } finally {
        setLoading(false);
      }
    };
    caricaPartite();
  }, [torneoId]);

  // Lista squadre uniche per il dropdown (calcolata dai dati)
  const squadreUniche = Array.from(
    new Set(partite.flatMap(p => [p.squadraCasa, p.squadraTrasferta]))
  ).sort();

  // Logica di filtraggio e ordinamento
  const partiteFiltrate = partite
    .filter(p => filtroStato === 'TUTTE' || p.status === filtroStato)
    .filter(p => 
      filtroSquadra === 'TUTTE' || 
      p.squadraCasa === filtroSquadra || 
      p.squadraTrasferta === filtroSquadra
    )
    .sort((a, b) => {
      const dataA = a.dataEOra ? new Date(a.dataEOra).getTime() : 0;
      const dataB = b.dataEOra ? new Date(b.dataEOra).getTime() : 0;
      return ordineData === 'ASC' ? dataA - dataB : dataB - dataA;
    });

  // Funzione helper per formattare la data in italiano
  const formattaData = (dataIso: string | null): string => {
    if (!dataIso) return '-';
    const data = new Date(dataIso);
    return data.toLocaleString('it-IT', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  // Funzione helper per mostrare il risultato o "Da giocare"
  const formattaRisultato = (p: Partita): string => {
    if (p.status === 'CANCELLED') return 'Annullata';
    if (p.goalsHome === null || p.goalsAway === null) return 'Da giocare';
    return `${p.goalsHome} - ${p.goalsAway}`;
  };

  // Colore del chip in base allo stato
  const coloreStato = (status: string): 'success' | 'warning' | 'error' | 'default' => {
    switch (status) {
      case 'PLAYED': return 'success';
      case 'SCHEDULED': return 'warning';
      case 'CANCELLED': return 'error';
      default: return 'default';
    }
  };

  if (loading) return <CircularProgress sx={{ display: 'block', margin: '20px auto' }} />;
  if (error) return <Alert severity="error">{error}</Alert>;

  return (
    <Box sx={{ p: 3, backgroundColor: 'white', borderRadius: 2, boxShadow: 3 }}>
      <Typography variant="h5" sx={{ fontWeight: 'bold', mb: 3, display: 'flex', alignItems: 'center' }}>
        <span style={{ marginRight: '10px' }}>📅</span> Calendario Partite
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
        alignItems: 'center',
        margin:'0 auto',
        width:'100',
        maxWidth:'900px'
      }}>
        {/* Filtro per Stato (toggle button) */}
        <Box>
          <Typography variant="body2" sx={{ mb: 1, fontWeight: 'bold' }}>Stato partita:</Typography>
          <ToggleButtonGroup
            value={filtroStato}
            exclusive
            onChange={(_, nuovoStato) => nuovoStato && setFiltroStato(nuovoStato)}
            size="small"
          >
            <ToggleButton value="TUTTE">Tutte</ToggleButton>
            <ToggleButton value="PLAYED">Giocate</ToggleButton>
            <ToggleButton value="SCHEDULED">Da giocare</ToggleButton>
            <ToggleButton value="CANCELLED">Annullate</ToggleButton>
          </ToggleButtonGroup>
        </Box>

        {/* Filtro per Squadra (dropdown) */}
        <FormControl size="small" sx={{ minWidth: 200 }}>
          <InputLabel>Squadra</InputLabel>
          <Select
            value={filtroSquadra}
            label="Squadra"
            onChange={(e: SelectChangeEvent) => setFiltroSquadra(e.target.value)}
          >
            <MenuItem value="TUTTE">Tutte le squadre</MenuItem>
            {squadreUniche.map(s => (
              <MenuItem key={s} value={s}>{s}</MenuItem>
            ))}
          </Select>
        </FormControl>

        {/* Ordinamento per Data */}
        <FormControl size="small" sx={{ minWidth: 180 }}>
          <InputLabel>Ordina per data</InputLabel>
          <Select
            value={ordineData}
            label="Ordina per data"
            onChange={(e: SelectChangeEvent) => setOrdineData(e.target.value as OrdineData)}
          >
            <MenuItem value="ASC">Più vecchie prima</MenuItem>
            <MenuItem value="DESC">Più recenti prima</MenuItem>
          </Select>
        </FormControl>
      </Box>

      {/* CONTEGGIO RISULTATI */}
      <Typography variant="body2" sx={{ mb: 2, color: 'text.secondary' }}>
        Partite trovate: <strong style={{ color: 'black' }}>{partiteFiltrate.length}</strong> su {partite.length}
      </Typography>

      {/* TABELLA PARTITE */}
      {partiteFiltrate.length > 0 ? (
        <TableContainer component={Paper} variant="outlined">
          <Table>
            <TableHead sx={{ backgroundColor: '#3498db' }}>
              <TableRow>
                <TableCell sx={{ color: 'white', fontWeight: 'bold' }}>Data e Ora</TableCell>
                <TableCell sx={{ color: 'white', fontWeight: 'bold' }}>Partita</TableCell>
                <TableCell sx={{ color: 'white', fontWeight: 'bold' }}>Risultato</TableCell>
                <TableCell sx={{ color: 'white', fontWeight: 'bold' }}>Stato</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {partiteFiltrate.map(p => (
                <TableRow key={p.id} hover>
                  <TableCell>{formattaData(p.dataEOra)}</TableCell>
                  <TableCell>
                    <strong>{p.squadraCasa}</strong> vs <strong>{p.squadraTrasferta}</strong>
                  </TableCell>
                  <TableCell sx={{ fontWeight: 'bold' }}>
                    {formattaRisultato(p)}
                  </TableCell>
                  <TableCell>
                    <Chip 
                      label={p.status} 
                      color={coloreStato(p.status)} 
                      size="small" 
                    />
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      ) : (
        <Alert severity="info">
          Nessuna partita trovata con i filtri selezionati.
        </Alert>
      )}
    </Box>
  );
}