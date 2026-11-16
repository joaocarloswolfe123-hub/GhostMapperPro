# GhostMapper Pro — versão inicial 0.1.0

Projeto Android nativo (Java), inspirado nas telas roxas/azuis do vídeo de referência fornecido.

## O que funciona nesta versão
- Tela inicial com tema escuro roxo
- Detecção do serviço Shizuku e solicitação de permissão
- Solicitação de permissão de sobreposição
- Editor flutuante demonstrativo, arrastável e fechável

## O que ainda NÃO funciona
- Conversão real de teclado/mouse em toques
- Perfis por jogo, gestos, mira e sensibilidade
- Reprodução exata de todas as telas e funções do vídeo
- APK compilado e testado

## Compilar
1. Instale Android Studio recente e SDK Android API 35.
2. Abra a pasta `GhostMapperPro` no Android Studio.
3. Aguarde a sincronização do Gradle (necessita internet para baixar dependências).
4. Selecione **Build > Build APK(s)**.
5. O APK de debug será criado em `app/build/outputs/apk/debug/app-debug.apk`.
6. Instale o app Shizuku no telefone, inicie o serviço pelo método suportado pelo aparelho e autorize o GhostMapper Pro.

Min SDK 26 (Android 8), target SDK 35. A versão 0.1 não garante suporte a todas as versões Android nem a todos os jogos. Não há binário APK neste ZIP.
