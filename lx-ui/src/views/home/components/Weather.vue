<template>
  <div class="app-container">
    <div class="header">
      <h1>Ai Generate Atmos Premium</h1>
      <p
        >Experience weather like never before with our premium animated
        forecasts</p
      >
    </div>

    <div class="weather-cards">
      <!-- Wind Card -->
      <div class="weather-card wind">
        <div class="premium-badge">Premium</div>
        <div class="weather-header">
          <div class="weather-title">Wind Storm</div>
          <div class="weather-icon">🌬️</div>
        </div>
        <div class="weather-content">
          <div class="temperature">23</div>
          <div class="weather-desc"
            >Strong winds up to 45 km/h with occasional gusts</div
          >
          <div class="weather-details">
            <div class="detail-item"
              ><span class="detail-icon">💨</span> Wind: 45 km/h</div
            >
            <div class="detail-item"
              ><span class="detail-icon">💧</span> Humidity: 65%</div
            >
            <div class="detail-item"
              ><span class="detail-icon">👁️</span> Visibility: 10 km</div
            >
            <div class="detail-item"
              ><span class="detail-icon">↗️</span> Pressure: 1012 hPa</div
            >
          </div>
        </div>
      </div>

      <!-- Rain Card -->
      <div class="weather-card rain">
        <div class="premium-badge">Premium</div>
        <div class="weather-header">
          <div class="weather-title">Heavy Rain</div>
          <div class="weather-icon">🌧️</div>
        </div>
        <div class="weather-content">
          <div class="temperature">18</div>
          <div class="weather-desc"
            >Persistent rainfall expected throughout the day</div
          >
          <div class="weather-details">
            <div class="detail-item"
              ><span class="detail-icon">☔</span> Precipitation: 95%</div
            >
            <div class="detail-item"
              ><span class="detail-icon">💧</span> Humidity: 85%</div
            >
            <div class="detail-item"
              ><span class="detail-icon">🌈</span> Rain: 12mm</div
            >
            <div class="detail-item"
              ><span class="detail-icon">↗️</span> Pressure: 1005 hPa</div
            >
          </div>
        </div>
      </div>

      <!-- Sun Card -->
      <div class="weather-card sun">
        <div class="premium-badge">Premium</div>
        <div class="weather-header">
          <div class="weather-title">Sunny</div>
          <div class="weather-icon">☀️</div>
        </div>
        <div class="weather-content">
          <div class="temperature">29</div>
          <div class="weather-desc">Clear skies with abundant sunshine</div>
          <div class="weather-details">
            <div class="detail-item"
              ><span class="detail-icon">🕶️</span> UV Index: 8</div
            >
            <div class="detail-item"
              ><span class="detail-icon">💧</span> Humidity: 40%</div
            >
            <div class="detail-item"
              ><span class="detail-icon">👁️</span> Visibility: 20 km</div
            >
            <div class="detail-item"
              ><span class="detail-icon">↗️</span> Pressure: 1018 hPa</div
            >
          </div>
        </div>
      </div>

      <!-- Snow Card -->
      <div class="weather-card snow">
        <div class="premium-badge">Premium</div>
        <div class="weather-header">
          <div class="weather-title">Snowfall</div>
          <div class="weather-icon">❄️</div>
        </div>
        <div class="weather-content">
          <div class="temperature">-3</div>
          <div class="weather-desc">Moderate snowfall accumulating 5-10 cm</div>
          <div class="weather-details">
            <div class="detail-item"
              ><span class="detail-icon">❄️</span> Snow: 8 cm</div
            >
            <div class="detail-item"
              ><span class="detail-icon">💧</span> Humidity: 75%</div
            >
            <div class="detail-item"
              ><span class="detail-icon">👁️</span> Visibility: 2 km</div
            >
            <div class="detail-item"
              ><span class="detail-icon">↗️</span> Pressure: 1020 hPa</div
            >
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
  import { onMounted } from 'vue';
  onMounted(() => {
    // Create wind elements
    const windCard = document.querySelector('.wind');

    // Create floating clouds
    for (let i = 0; i < 3; i++) {
      const particle = document.createElement('div');
      particle.classList.add('particle');
      windCard.appendChild(particle);
    }

    // Create wind lines
    for (let i = 0; i < 8; i++) {
      const line = document.createElement('div');
      line.classList.add('wind-line');
      line.style.top = Math.random() * 350 + 'px';
      line.style.left = Math.random() * 300 + 'px';
      line.style.width = Math.random() * 100 + 50 + 'px';
      line.style.animationDelay = Math.random() * 4 + 's';
      windCard.appendChild(line);
    }

    // Create rain drops
    const rainCard = document.querySelector('.rain');
    for (let i = 0; i < 60; i++) {
      const drop = document.createElement('div');
      drop.classList.add('drop');
      drop.style.left = Math.random() * 300 + 'px';
      drop.style.height = Math.random() * 10 + 5 + 'px';
      drop.style.animationDuration = Math.random() * 0.5 + 0.8 + 's';
      drop.style.animationDelay = Math.random() * 2 + 's';
      rainCard.appendChild(drop);

      // Create ripples at the bottom
      if (i % 5 === 0) {
        const ripple = document.createElement('div');
        ripple.classList.add('ripple');
        ripple.style.left = Math.random() * 280 + 10 + 'px';
        ripple.style.width = Math.random() * 30 + 10 + 'px';
        ripple.style.height = ripple.style.width;
        ripple.style.animationDelay = Math.random() * 2 + 's';
        rainCard.appendChild(ripple);
      }
    }

    // Create sun rays
    const sunCard = document.querySelector('.sun');
    for (let i = 0; i < 12; i++) {
      const ray = document.createElement('div');
      ray.classList.add('sun-ray');
      ray.style.transform = `rotate(${i * 30}deg)`;
      ray.style.animationDelay = Math.random() * 4 + 's';
      sunCard.appendChild(ray);
    }

    const sunCore = document.createElement('div');
    sunCore.classList.add('sun-core');
    sunCard.appendChild(sunCore);

    // Create snow flakes
    const snowCard = document.querySelector('.snow');
    for (let i = 0; i < 100; i++) {
      const flake = document.createElement('div');
      flake.classList.add('flake');
      flake.style.left = Math.random() * 300 + 'px';
      const size = Math.random() * 4 + 2;
      flake.style.width = size + 'px';
      flake.style.height = size + 'px';
      flake.style.opacity = Math.random() * 0.5 + 0.5;
      flake.style.animationDuration = Math.random() * 10 + 5 + 's';
      flake.style.animationDelay = Math.random() * 10 + 's';
      snowCard.appendChild(flake);
    }

    const snowLayer = document.createElement('div');
    snowLayer.classList.add('snow-layer');
    snowCard.appendChild(snowLayer);

    // Add hover effect to weather cards
    const cards = document.querySelectorAll('.weather-card');
    cards.forEach((card) => {
      card.addEventListener('mousemove', (e) => {
        const rect = card.getBoundingClientRect();
        const x = e.clientX - rect.left;
        const y = e.clientY - rect.top;
        const centerX = rect.width / 2;
        const centerY = rect.height / 2;
        const angle = (Math.atan2(y - centerY, x - centerX) * 180) / Math.PI;

        card.style.transform = `perspective(1000px) rotateY(${(x - centerX) / 30}deg) rotateX(${(centerY - y) / 30}deg) scale(2)`;
      });

      card.addEventListener('mouseleave', () => {
        card.style.transform =
          'perspective(1000px) rotateY(0) rotateX(0) scale(1)';
      });
    });
  });
</script>
<style>
  .app-container {
    --card-width: 300px;
    --card-height: 450px;
    --primary: #007aff;
    --primary-light: rgba(0, 122, 255, 0.1);
    --text-light: rgba(255, 255, 255, 0.9);
    --text-lighter: rgba(255, 255, 255, 0.7);
    --transition: all 0.4s cubic-bezier(0.2, 0.9, 0.4, 1);
    background: radial-gradient(circle at center, #0a0e23 0%, #0a0a18 100%);
    font-family:
      'SF Pro Display',
      -apple-system,
      BlinkMacSystemFont,
      sans-serif;
    color: var(--text-light);
    overflow-x: hidden;
    perspective: 1000px;
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
    padding: 40px;
    height: 100vh;
  }

  .header {
    text-align: center;
    margin-bottom: 40px;
    animation: fadeIn 1s ease-out;
  }

  .header h1 {
    font-size: 2.5rem;
    font-weight: 700;
    margin-bottom: 10px;
    background: linear-gradient(90deg, #fff, #a7d2ff);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    letter-spacing: -0.5px;
  }

  .header p {
    font-size: 1.1rem;
    opacity: 0.8;
    max-width: 600px;
    margin: 0 auto;
    line-height: 1.6;
  }

  .weather-cards {
    display: flex;
    flex-wrap: wrap;
    gap: 30px;
    justify-content: center;
    width: 100%;
  }

  .weather-card {
    width: var(--card-width);
    height: var(--card-height);
    background: rgba(20, 25, 50, 0.6);
    backdrop-filter: blur(10px);
    border-radius: 24px;
    box-shadow: 0 20px 50px rgba(0, 0, 0, 0.3);
    position: relative;
    overflow: hidden;
    display: flex;
    flex-direction: column;
    padding: 30px;
    transition: var(--transition);
    border: 1px solid rgba(255, 255, 255, 0.05);
    animation: floatIn 0.6s ease-out both;
    cursor: pointer;
  }

  .weather-card:hover {
    transform: translateY(-10px) scale(1.02);
    box-shadow: 0 30px 60px rgba(0, 0, 0, 0.4);
  }

  .weather-card:nth-child(1) {
    animation-delay: 0.1s;
  }
  .weather-card:nth-child(2) {
    animation-delay: 0.2s;
  }
  .weather-card:nth-child(3) {
    animation-delay: 0.3s;
  }
  .weather-card:nth-child(4) {
    animation-delay: 0.4s;
  }

  .weather-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    z-index: 2;
  }

  .weather-title {
    font-size: 1.5rem;
    font-weight: 600;
    color: white;
  }

  .weather-icon {
    font-size: 2.5rem;
    width: 60px;
    height: 60px;
    display: flex;
    align-items: center;
    justify-content: center;
    background: var(--primary-light);
    border-radius: 50%;
    transition: var(--transition);
  }

  .weather-content {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    z-index: 2;
    margin: 20px 0;
  }

  .temperature {
    font-size: 4.5rem;
    font-weight: 200;
    line-height: 1;
    margin: 20px 0;
    background: linear-gradient(180deg, #fff, #d1e7ff);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    position: relative;
  }

  .temperature::after {
    content: '°';
    position: absolute;
    top: 0;
    font-size: 2rem;
  }

  .weather-desc {
    text-align: center;
    font-size: 1rem;
    opacity: 0.8;
    margin-top: 10px;
    line-height: 1.6;
  }

  .weather-details {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 15px;
    width: 100%;
    margin-top: 20px;
    z-index: 2;
  }

  .detail-item {
    display: flex;
    align-items: center;
    font-size: 0.9rem;
  }

  .detail-icon {
    margin-right: 8px;
    opacity: 0.7;
  }

  .premium-badge {
    position: absolute;
    top: 20px;
    right: 20px;
    background: linear-gradient(135deg, #ff9a9e 0%, #fad0c4 100%);
    color: #000;
    padding: 4px 10px;
    border-radius: 20px;
    font-size: 0.7rem;
    font-weight: 600;
    text-transform: uppercase;
    z-index: 3;
    box-shadow: 0 4px 15px rgba(255, 154, 158, 0.3);
  }

  /* Wind Card */
  .wind {
    background: linear-gradient(
      135deg,
      rgba(40, 50, 90, 0.6) 0%,
      rgba(30, 40, 80, 0.8) 100%
    );
  }

  .wind .weather-icon {
    background: rgba(0, 122, 255, 0.2);
  }

  .wind .particle {
    position: absolute;
    background-color: rgba(255, 255, 255, 0.15);
    border-radius: 50%;
    filter: blur(1px);
    animation: windFloat 20s linear infinite;
  }

  .wind .particle:nth-child(1) {
    width: 120px;
    height: 60px;
    top: 50px;
    left: -150px;
    animation-duration: 25s;
  }

  .wind .particle:nth-child(2) {
    width: 80px;
    height: 40px;
    top: 120px;
    left: -100px;
    animation-duration: 20s;
    animation-delay: 5s;
  }

  .wind .particle:nth-child(3) {
    width: 150px;
    height: 75px;
    top: 200px;
    left: -180px;
    animation-duration: 30s;
    animation-delay: 10s;
  }

  .wind .wind-line {
    position: absolute;
    height: 1px;
    background: linear-gradient(
      90deg,
      rgba(255, 255, 255, 0),
      rgba(255, 255, 255, 0.4),
      rgba(255, 255, 255, 0)
    );
    animation: windLine 4s linear infinite;
    transform-origin: left center;
  }

  @keyframes windFloat {
    0% {
      transform: translateX(0) translateY(0);
      opacity: 0;
    }
    10% {
      opacity: 0.8;
    }
    90% {
      opacity: 0.8;
    }
    100% {
      transform: translateX(600px) translateY(-20px);
      opacity: 0;
    }
  }

  @keyframes windLine {
    0% {
      transform: scaleX(0);
      opacity: 0;
    }
    20% {
      opacity: 1;
    }
    80% {
      opacity: 1;
    }
    100% {
      transform: scaleX(1);
      opacity: 0;
    }
  }

  /* Rain Card */
  .rain {
    background: linear-gradient(
      135deg,
      rgba(30, 50, 80, 0.6) 0%,
      rgba(20, 40, 70, 0.8) 100%
    );
  }

  .rain .weather-icon {
    background: rgba(100, 180, 255, 0.2);
  }

  .rain .drop {
    position: absolute;
    width: 2px;
    background: linear-gradient(
      180deg,
      rgba(200, 230, 255, 0.8),
      rgba(150, 200, 255, 0.8)
    );
    border-radius: 0 0 3px 3px;
    animation: rainFall linear infinite;
    z-index: 1;
  }

  .rain .ripple {
    position: absolute;
    bottom: 0;
    border-radius: 50%;
    border: 1px solid rgba(100, 180, 255, 0.4);
    animation: ripple 2s ease-out infinite;
    transform: scale(0);
    opacity: 0;
    z-index: 1;
  }

  @keyframes rainFall {
    0% {
      transform: translateY(-100px) translateX(0);
      opacity: 0;
    }
    10% {
      opacity: 1;
    }
    90% {
      opacity: 1;
    }
    100% {
      transform: translateY(450px) translateX(10px);
      opacity: 0;
    }
  }

  @keyframes ripple {
    0% {
      transform: scale(0);
      opacity: 0.6;
    }
    100% {
      transform: scale(3);
      opacity: 0;
    }
  }

  /* Sun Card */
  .sun {
    background: linear-gradient(
      135deg,
      rgba(255, 180, 0, 0.1) 0%,
      rgba(255, 120, 0, 0.2) 100%
    );
  }

  .sun .weather-icon {
    background: rgba(255, 200, 0, 0.3);
  }

  .sun .sun-core {
    position: absolute;
    width: 120px;
    height: 120px;
    background: radial-gradient(
      circle,
      rgba(255, 230, 100, 0.8) 0%,
      rgba(255, 180, 0, 0) 70%
    );
    border-radius: 50%;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    z-index: 1;
    animation: pulse 4s ease-in-out infinite;
  }

  .sun .sun-ray {
    position: absolute;
    width: 2px;
    height: 200px;
    background: linear-gradient(
      180deg,
      rgba(255, 230, 100, 0),
      rgba(255, 230, 100, 0.3),
      rgba(255, 230, 100, 0)
    );
    top: 50%;
    left: 50%;
    transform-origin: top center;
    z-index: 1;
    animation: rayPulse 4s ease-in-out infinite;
  }

  @keyframes pulse {
    0%,
    100% {
      transform: translate(-50%, -50%) scale(0.9);
      opacity: 0.8;
    }
    50% {
      transform: translate(-50%, -50%) scale(1.1);
      opacity: 1;
    }
  }

  @keyframes rayPulse {
    0%,
    100% {
      opacity: 0.3;
    }
    50% {
      opacity: 0.6;
    }
  }

  /* Snow Card */
  .snow {
    background: linear-gradient(
      135deg,
      rgba(30, 50, 80, 0.6) 0%,
      rgba(20, 40, 70, 0.8) 100%
    );
  }

  .snow .weather-icon {
    background: rgba(200, 230, 255, 0.2);
  }

  .snow .flake {
    position: absolute;
    background: white;
    border-radius: 50%;
    filter: blur(1px);
    opacity: 0.9;
    animation: snowDrift linear infinite;
    z-index: 1;
  }

  .snow .snow-layer {
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    background: linear-gradient(
      180deg,
      rgba(255, 255, 255, 0),
      rgba(255, 255, 255, 0.2)
    );
    border-radius: 0 0 24px 24px;
    animation: snowAccumulate 15s linear forwards;
    z-index: 1;
  }

  @keyframes snowDrift {
    0% {
      transform: translateY(-100px) translateX(0) rotate(0deg);
      opacity: 0;
    }
    10% {
      opacity: 0.8;
    }
    90% {
      opacity: 0.8;
    }
    100% {
      transform: translateY(450px) translateX(30px) rotate(360deg);
      opacity: 0;
    }
  }

  @keyframes snowAccumulate {
    0% {
      height: 0;
      opacity: 0;
    }
    10% {
      opacity: 1;
    }
    100% {
      height: 40px;
      opacity: 1;
    }
  }

  /* Animations */
  @keyframes floatIn {
    0% {
      opacity: 0;
      transform: translateY(30px) rotateX(20deg);
    }
    100% {
      opacity: 1;
      transform: translateY(0) rotateX(0);
    }
  }

  @keyframes fadeIn {
    0% {
      opacity: 0;
      transform: translateY(-20px);
    }
    100% {
      opacity: 1;
      transform: translateY(0);
    }
  }

  /* Responsive */
  @media (max-width: 1300px) {
    .weather-cards {
      grid-template-columns: repeat(2, 1fr);
    }
  }

  @media (max-width: 768px) {
    .weather-cards {
      grid-template-columns: 1fr;
    }

    .weather-card {
      width: 100%;
      max-width: 400px;
    }
  }
</style>
