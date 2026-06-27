import { AppBar, Toolbar, Typography, Button, Box, CssBaseline } from '@mui/material';
import { Link as RouterLink, Outlet, useLocation } from 'react-router-dom';

export default function Layout() {
  const location = useLocation();

  // Funzione per evidenziare il link della pagina attiva
  const isActive = (path: string) => location.pathname === path;

  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
      <CssBaseline />

      {/* NAVBAR FISSA IN ALTO */}
      <AppBar position="static" color="inherit" elevation={1}>
        <Toolbar>
          <Typography variant="h6" sx={{ flexGrow: 1, fontWeight: 'bold' }}>
            <span style={{ marginRight: '8px' }}>🏆</span>Gestione Tornei
          </Typography>

          {/* LINK DI NAVIGAZIONE */}
          <Button
            component={RouterLink}
            to="/"
            sx={{
              textTransform: 'none',
              fontWeight: 'bold',
              color: isActive('/') ? '#d35400' : 'inherit'
            }}
          >
            Tornei
          </Button>

          

          <Button
            component={RouterLink}
            to="/giocatori"
            sx={{
              textTransform: 'none',
              fontWeight: 'bold',
              color: isActive('/giocatori') ? '#d35400' : 'inherit'
            }}
          >
            Giocatori
          </Button>
        </Toolbar>
      </AppBar>

      {/* AREA CONTENUTO: qui React Router inserisce la pagina attiva */}
      <Box
        sx={{
          flexGrow: 1,
          backgroundColor: '#f0f2f5',
          display: 'flex',
          alignItems: 'flex-start',
          justifyContent: 'center',
          padding: 3,
          overflowY: 'auto'
        }}
      >
        <Outlet />
      </Box>
    </Box>
  );
}