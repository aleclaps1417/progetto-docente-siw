import { useState, useEffect } from 'react';
import { Card, CardContent, Typography, CircularProgress, Alert, Box, Button, TextField } from '@mui/material';
import type { Torneo } from '../types';
import { getTornei } from '../services/torneoService';
import { Link as RouterLink } from 'react-router-dom';
export default function TorneiList() {
  const [tornei, setTornei] = useState<Torneo[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  
  // STATO PER IL FILTRO
  const [yearFilter, setYearFilter] = useState<number | null>(null);

  useEffect(() => {
    const caricaDati = async () => {
      try {
        const dati = await getTornei();
        setTornei(dati); 
      } catch (err) {
        setError(err instanceof Error ? err.message : "Errore sconosciuto");
      } finally {
        setLoading(false); 
      }
    };
    caricaDati();
  }, []);

  // LOGICA DI FILTRAGGIO
  const torneiFiltrati = yearFilter 
    ? tornei.filter(torneo => torneo.anno === yearFilter) 
    : tornei;

  if (loading) return <CircularProgress sx={{ display: 'block', margin: '20px auto', color: 'white' }} />;
  if (error) return <Alert severity="error">{error}</Alert>;

  return (
    <Box 
      sx={{ 
        backgroundColor: 'white', 
        padding: 4, 
        borderRadius: 2, 
        boxShadow: 24, 
        width: '100%', 
        maxWidth: '700px',
        textAlign: 'left' // Riportiamo il testo allineato a sinistra per eleganza
      }}
    >
      <Typography variant="h5" sx={{ fontWeight: 'bold', mb: 3, display: 'flex', alignItems: 'center' }}>
        <span style={{ marginRight: '10px' }}>🏆</span> Tornei Disponibili
      </Typography>

      {/* AREA DEL FILTRO */}
      <Box sx={{ mb: 3, p: 2, backgroundColor: '#f8f9fa', borderRadius: 2, border: '1px solid #e0e0e0' }}>
        <TextField 
          fullWidth
          label="Filtra per Anno (Es: 2026)"
          type="number"
          variant="outlined"
          size="small"
          sx={{ backgroundColor: 'white' }}
          onChange={(e) => setYearFilter(e.target.value ? parseInt(e.target.value) : null)}
        />
        <Typography variant="body2" sx={{ mt: 1.5, color: 'text.secondary' }}>
          Tornei trovati: <strong style={{ color: 'black' }}>{torneiFiltrati.length}</strong>
        </Typography>
      </Box>

      {/* LISTA TORNEI */}
      {torneiFiltrati.map((torneo) => (
        <Card key={torneo.id} variant="outlined" sx={{ mb: 2, borderRadius: 2 }}>
          <CardContent>
            <Typography variant="h6" sx={{ fontWeight: 'bold', color: '#d35400', mb: 1 }}>
              {torneo.nome}
            </Typography>
            
            <Typography sx={{ mb: 1.5, fontSize: '0.9rem' }}>
              <strong>Anno:</strong> {torneo.anno}
            </Typography>

            {torneo.descrizione && (
              <Typography variant="body2" color="text.secondary" sx={{ mb: 2 }}>
                {torneo.descrizione}
              </Typography>
            )}

            
            <Button 
                variant="contained" 
                color="primary" 
                disableElevation 
                component={RouterLink}
                to={`/torneo/${torneo.id}/calendario`}
                sx={{ textTransform: 'none', borderRadius: 1 }}
              >
            Vedi Calendario →
          </Button>
          </CardContent>
        </Card>
      ))}

      {/* MESSAGGIO RICERCA VUOTA */}
      {torneiFiltrati.length === 0 && tornei.length > 0 && (
        <Typography sx={{ color: '#d32f2f', fontWeight: 'bold', mt: 2 }}>
          Nessun torneo trovato per l'anno {yearFilter}.
        </Typography>
      )}

      
    </Box>
  );
}