import React from 'react';

// Qui definiamo il "tipo" di dati che questa card si aspetta (TypeScript)
interface TorneoCardProps {
  nome: string;
  anno: number;
  descrizione?: string; // Il "?" significa che la descrizione è opzionale
}

// Il componente funzionale vero e proprio
export function TorneoCard({ nome, anno, descrizione }: TorneoCardProps) {
  return (
    <div style={{ border: '1px solid #ccc', padding: '15px', margin: '10px 0', borderRadius: '8px', backgroundColor: '#f8f9fa' }}>
      <h2 style={{ margin: '0 0 10px 0', color: '#007bff' }}>🏆 {nome}</h2>
      <p style={{ margin: '5px 0' }}><strong>Anno Edizione:</strong> {anno}</p>
      
      {/* Rendering condizionale: stampa il <p> solo se la descrizione esiste */}
      {descrizione && <p style={{ margin: '5px 0', fontStyle: 'italic', color: '#555' }}>{descrizione}</p>} 
    </div>
  );
}